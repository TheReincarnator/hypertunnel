package de.thomasjacob.hypertunnel.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class HyperTunnelServlet extends HttpServlet {

	private HyperTunnelServer server;

	/**
	 * Creates a new HyperTunnelHandler.
	 *
	 * @param server
	 */
	public HyperTunnelServlet() {
		server = new HyperTunnelServer();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		server.handleWelcome(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (StringUtils.equalsIgnoreCase(action, "send")) {
			server.handleSend(request, response);
		} else if (StringUtils.equalsIgnoreCase(action, "receive")) {
			server.handleReceive(request, response);
		}
	}
}
