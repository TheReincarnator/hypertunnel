package de.thomasjacob.hypertunnel.client.tunnel.http;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.EnumerationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HttpTunnelHandler extends AbstractHandler {

	private static final Set<String> REQUEST_HEADERS_TO_STRIP = new HashSet<String>();

	static {
		REQUEST_HEADERS_TO_STRIP.add("connection");
		REQUEST_HEADERS_TO_STRIP.add("content-length");
		REQUEST_HEADERS_TO_STRIP.add("content-type");
		REQUEST_HEADERS_TO_STRIP.add("proxy-authorization");
		REQUEST_HEADERS_TO_STRIP.add("proxy-connection");
		REQUEST_HEADERS_TO_STRIP.add("transfer-encoding");
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		StringBuilder builder = new StringBuilder();
		builder.append(request.getMethod());
		builder.append(" ");

		builder.append(request.getRequestURI());
		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			builder.append("?").append(queryString);
		}
		builder.append(" ");

		builder.append(request.getProtocol());
		builder.append("\n");

		String host = null;
		for (String headerName : EnumerationUtils.toList(request.getHeaderNames())) {
			if (REQUEST_HEADERS_TO_STRIP.contains(headerName.toLowerCase())) {
				continue;
			}

			for (String headerValue : EnumerationUtils.toList(request.getHeaders(headerName))) {
				if (headerName.equalsIgnoreCase("Host")) {
					host = headerValue;
				}

				builder.append(headerName);
				builder.append(": ");
				builder.append(headerValue);
				builder.append("\n");
			}
		}

		byte[] headerBytes = builder.toString().getBytes("UTF-8");
		byte[] bodyBytes = IOUtils.toByteArray(request.getInputStream());
	}
}
