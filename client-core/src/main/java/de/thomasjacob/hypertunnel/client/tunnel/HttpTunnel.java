package de.thomasjacob.hypertunnel.client.tunnel;

/**
 * @author thomas
 */
public class HttpTunnel extends Tunnel {

	private int sourcePort;

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
