package de.thomasjacob.hypertunnel.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HyperTunnelHandler extends AbstractHandler {

	private HyperTunnelServer server;

	/**
	 * Creates a new HyperTunnelHandler.
	 *
	 * @param server
	 */
	public HyperTunnelHandler(HyperTunnelServer server) {
		this.server = server;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		if (StringUtils.equalsIgnoreCase(request.getMethod(), "GET")) {
			server.handleWelcome(request, response);
		} else if (StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			String action = request.getParameter("action");
			if (StringUtils.equalsIgnoreCase(action, "send")) {
				server.handleSend(request, response);
			} else if (StringUtils.equalsIgnoreCase(action, "receive")) {
				server.handleReceive(request, response);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
