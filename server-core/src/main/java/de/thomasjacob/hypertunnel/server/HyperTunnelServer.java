package de.thomasjacob.hypertunnel.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Thomas
 */
public class HyperTunnelServer {

	private int maxPollTime = 10000;
	private MapOfLists<String, Message> pendingMessages = new MapOfLists<String, Message>();

	/**
	 * Returns the maxPollTime.
	 *
	 * @return The maxPollTime.
	 */
	public int getMaxPollTime() {
		return maxPollTime;
	}

	public void handleReceive(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String client = StringUtils.defaultString(request.getParameter("client"));
		System.out.println("Processing receive request for " + client);

		long maxTime = System.currentTimeMillis() + maxPollTime;
		while (System.currentTimeMillis() < maxTime) {
			Message message;
			synchronized (pendingMessages) {
				message = pendingMessages.remove(client, 0);
			}

			if (message != null && System.currentTimeMillis() - message.getCreationTime() > maxPollTime * 3) {
				message = null;
			}

			if (message != null) {
				byte[] prefix = (message.getSourceClient() + ":" + message.getCategory() + ":").getBytes("UTF-8");
				System.out.println("Sending response for " + client + ": " + message.getSourceClient() + ":"
					+ message.getCategory());

				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentLength(prefix.length + message.getPayload().length);
				response.setContentType("application/unknown");
				response.getOutputStream().write(prefix);
				response.getOutputStream().write(message.getPayload());
				response.getOutputStream().flush();
				return;
			}

			synchronized (pendingMessages) {
				try {
					pendingMessages.wait(maxPollTime);
				} catch (InterruptedException exception) {
					// Ignored
				}
			}
		}

		System.out.println("No message for " + client);
		sendText(response, "OK");
	}

	public void handleSend(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetClient = StringUtils.defaultString(request.getParameter("targetClient"));

		String sourceClient = StringUtils.defaultString(request.getParameter("sourceClient"));
		String category = StringUtils.defaultString(request.getParameter("category"));
		String payloadString = StringUtils.defaultString(request.getParameter("payload"));
		byte[] payload = Base64.decodeBase64(payloadString);
		Message message = new Message(sourceClient, category, payload);

		System.out.println("Processing send request " + sourceClient + ":" + targetClient + ":" + category);

		synchronized (pendingMessages) {
			pendingMessages.add(targetClient, message);
			pendingMessages.notifyAll();
		}

		sendText(response, "OK");
	}

	public void handleWelcome(HttpServletRequest request, HttpServletResponse response) throws IOException {
		sendText(response, "Hello world");
	}

	private void sendText(HttpServletResponse response, String text) throws UnsupportedEncodingException, IOException {
		byte[] bytes = text.getBytes("UTF-8");

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentLength(bytes.length);
		response.setContentType("text/plain; charset=UTF-8");
		response.getOutputStream().write(bytes);
	}

	/**
	 * Sets the maxPollTime.
	 *
	 * @param maxPollTime The maxPollTime.
	 */
	public void setMaxPollTime(int maxPollTime) {
		this.maxPollTime = maxPollTime;
	}
}
