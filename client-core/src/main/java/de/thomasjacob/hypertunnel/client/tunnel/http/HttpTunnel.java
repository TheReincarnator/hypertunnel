package de.thomasjacob.hypertunnel.client.tunnel.http;

import de.thomasjacob.hypertunnel.client.tunnel.Tunnel;

/**
 * @author thomas
 */
public class HttpTunnel extends Tunnel {

	private int sourcePort;

	public HttpTunnel(String[] parameters) {
		if (parameters.length != 2) {
			throw new IllegalArgumentException("HTTP tunnels require 2 parameters (source-port:client)");
		}

		sourcePort = Integer.parseInt(parameters[0]);
		setTargetClient(parameters[1]);
	}

	/**
	 * Returns the sourcePort.
	 *
	 * @return The sourcePort.
	 */
	public int getSourcePort() {
		return sourcePort;
	}

	/**
	 * Sets the sourcePort.
	 *
	 * @param sourcePort The sourcePort.
	 */
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}
}
