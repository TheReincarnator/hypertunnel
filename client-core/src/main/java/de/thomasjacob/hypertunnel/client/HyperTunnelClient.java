package de.thomasjacob.hypertunnel.client;

import de.thomasjacob.hypertunnel.client.delegate.Delegate;
import de.thomasjacob.hypertunnel.client.tunnel.Tunnel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

/**
 * @author Thomas
 */
public class HyperTunnelClient implements Runnable {

	private Map<String, Delegate> delegates = new HashMap<String, Delegate>();
	private String id;
	private boolean running = false;
	private String serverUrl;
	private List<Tunnel> tunnels = new ArrayList<Tunnel>();

	public void addDelegate(Delegate delegate) {
		delegates.put(delegate.getCategory(), delegate);
	}

	public synchronized void addTunnel(Tunnel tunnel) {
		if (tunnels.contains(tunnel)) {
			return;
		}

		tunnels.add(tunnel);

		if (running) {
			tunnel.start();
		}
	}

	/**
	 * Returns the delegates.
	 *
	 * @return The delegates.
	 */
	public Map<String, Delegate> getDelegates() {
		return delegates;
	}

	/**
	 * Returns the id.
	 *
	 * @return The id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the serverUrl.
	 *
	 * @return The serverUrl.
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * Returns the running.
	 *
	 * @return The running.
	 */
	public boolean isRunning() {
		return running;
	}

	private Message receiveMessage() {
		try {
			BasicHttpParams params = new BasicHttpParams();
			params.setParameter("action", "receive");
			params.setParameter("client", id);

			HttpRequestBase requestMethod = new HttpPost(serverUrl);
			requestMethod.setParams(params);
			HttpResponse response;
			try {
				response = new SystemDefaultHttpClient().execute(requestMethod);
			} catch (Exception exception) {
				throw new IOException("Failed to connect to hypertunnel server: " + exception.getMessage(), exception);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Failed to receive data from hypertunnel server: HTTP response code "
					+ response.getStatusLine().getStatusCode());
			}

			byte[] responseData;
			try {
				responseData = EntityUtils.toByteArray(response.getEntity());
			} catch (Exception exception) {
				throw new IOException("Failed to parse data from hypertunnel server: " + exception.getMessage());
			}

			if (responseData == null || responseData.length <= 2) {
				return null;
			}

			int separatorPos1 = ArrayUtils.indexOf(responseData, (byte) ':');
			if (separatorPos1 < 0) {
				throw new IOException(
					"Failed to parse data from hypertunnel server: Wrong format, should be id:category:payload");
			}

			int separatorPos2 = ArrayUtils.indexOf(responseData, (byte) ':', separatorPos1 + 1);
			if (separatorPos2 < 0) {
				throw new IOException(
					"Failed to parse data from hypertunnel server: Wrong format, should be id:category:payload");
			}

			Message message = new Message();
			message.sourceClient = new String(responseData, 0, separatorPos1, "UTF-8");
			message.category = new String(responseData, separatorPos1 + 1, separatorPos2 - separatorPos1 - 1, "UTF-8");
			message.payload = ArrayUtils.subarray(responseData, separatorPos2 + 1, responseData.length);

			return message;
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
			return null;
		}
	}

	public void removeDelegate(Delegate delegate) {
		delegates.remove(delegate.getCategory());
	}

	public void removeDelegate(String category) {
		delegates.remove(category);
	}

	public synchronized void removeTunnel(Tunnel tunnel) {
		if (!tunnels.contains(tunnel)) {
			return;
		}

		if (running) {
			tunnel.stop();
		}

		tunnels.remove(tunnel);
	}

	@Override
	public synchronized void run() {
		if (!running) {
			return;
		}

		running = true;

		for (Tunnel tunnel : tunnels) {
			tunnel.setSourceClient(this);
			tunnel.start();
		}

		while (running) {
			Message message = receiveMessage();
			if (message == null) {
				continue;
			}

			Delegate delegate = delegates.get(message.category);
			if (delegate != null) {
				delegate.execute(message.sourceClient, message.payload);
			}
		}

		for (Tunnel tunnel : tunnels) {
			tunnel.stop();
		}
	}

	public void sendMessage(String targetClient, String category, byte[] payload) {
		synchronized (this) {
			if (!running) {
				// We are currently shutting down, ignore all further send requests
				return;
			}
		}

		try {
			BasicHttpParams params = new BasicHttpParams();
			params.setParameter("action", "send");
			params.setParameter("sourceClient", id);
			params.setParameter("targetClient", targetClient);
			params.setParameter("category", category);
			params.setParameter("payload", Base64.encodeBase64String(payload));

			HttpRequestBase requestMethod = new HttpPost(serverUrl);
			HttpResponse response;
			try {
				response = new SystemDefaultHttpClient().execute(requestMethod);
			} catch (Exception exception) {
				throw new IOException("Failed to connect to hypertunnel server: " + exception.getMessage(), exception);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new IOException("Failed to send data to hypertunnel server: HTTP response code "
					+ response.getStatusLine().getStatusCode());
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
		}
	}

	public void sendMessage(String targetClient, String category, String payloadString) {
		byte[] payload;
		try {
			payload = payloadString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException exception) {
			throw new IllegalStateException("Cannot encode payload", exception);
		}

		sendMessage(targetClient, category, payload);
	}

	/**
	 * Sets the delegates.
	 *
	 * @param delegates The delegates.
	 */
	public void setDelegates(Map<String, Delegate> delegates) {
		this.delegates = delegates;
	}

	/**
	 * Sets the id.
	 *
	 * @param id The id.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the serverUrl.
	 *
	 * @param serverUrl The serverUrl.
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public synchronized void stop() {
		running = false;
		notifyAll();
	}

	private static class Message {
		private String category;
		private byte[] payload;
		private String sourceClient;
	}
}
