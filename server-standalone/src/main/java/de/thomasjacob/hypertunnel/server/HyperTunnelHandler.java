package de.thomasjacob.hypertunnel.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HyperTunnelHandler extends AbstractHandler {
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		// TODO
		throw new UnsupportedOperationException("TODO");
	}
}
