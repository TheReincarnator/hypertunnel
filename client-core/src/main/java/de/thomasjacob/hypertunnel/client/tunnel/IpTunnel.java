package de.thomasjacob.hypertunnel.client.tunnel;

/**
 * @author thomas
 */
public class IpTunnel extends Tunnel {

	private int sourcePort;
	private int targetPort;

	/**
	 * Returns the sourcePort.
	 *
	 * @return The sourcePort.
	 */
	public int getSourcePort() {
		return sourcePort;
	}

	/**
	 * Returns the targetPort.
	 *
	 * @return The targetPort.
	 */
	public int getTargetPort() {
		return targetPort;
	}

	/**
	 * Sets the sourcePort.
	 *
	 * @param sourcePort The sourcePort.
	 */
	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	/**
	 * Sets the targetPort.
	 *
	 * @param targetPort The targetPort.
	 */
	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}
}
