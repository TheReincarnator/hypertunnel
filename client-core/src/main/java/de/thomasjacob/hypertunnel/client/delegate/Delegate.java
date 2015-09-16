package de.thomasjacob.hypertunnel.client.delegate;

import java.io.UnsupportedEncodingException;

/**
 * @author thomas
 */
public abstract class Delegate {

	protected String decodePayload(byte[] payload) {
		try {
			return new String(payload, "UTF-8");
		} catch (UnsupportedEncodingException exception) {
			throw new IllegalStateException("Cannot decode payload", exception);
		}
	}

	public abstract void execute(String sourceClient, byte[] payload);

	public abstract String getCategory();
}
