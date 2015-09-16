package de.thomasjacob.hypertunnel.client.delegate;

import java.util.HashSet;
import java.util.Set;

/**
 * @author thomas
 */
public class HttpDelegate extends Delegate {

	public static final String CATEGORY = "http";

	private static final Set<String> RESPONSE_HEADERS_TO_STRIP = new HashSet<String>();

	static {
		RESPONSE_HEADERS_TO_STRIP.add("connection");
		RESPONSE_HEADERS_TO_STRIP.add("keep-alive");
		RESPONSE_HEADERS_TO_STRIP.add("server");
		RESPONSE_HEADERS_TO_STRIP.add("proxy-connection");
		RESPONSE_HEADERS_TO_STRIP.add("transfer-encoding");
	}

	@Override
	public void execute(String sourceClient, byte[] payload) {
	}

	@Override
	public String getCategory() {
		return CATEGORY;
	}

}
