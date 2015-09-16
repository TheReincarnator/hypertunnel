package de.thomasjacob.hypertunnel.client.tunnel;

/**
 * @author thomas
 */
public class IpTunnel extends Tunnel {

	private int sourcePort;
	private String targetHost;
	private int targetPort;

	public IpTunnel(String[] parameters) {
		if (parameters.length != 4) {
			throw new IllegalArgumentException(
				"IP tunnels require 4 parameters (source-port:client:target-host:target-port)");
		}

		sourcePort = Integer.parseInt(parameters[0]);
		setTargetClient(parameters[1]);
		targetHost = parameters[2];
		targetPort = Integer.parseInt(parameters[3]);
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
	 * Returns the targetHost.
	 *
	 * @return The targetHost.
	 */
	public String getTargetHost() {
		return targetHost;
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
	 * Sets the targetHost.
	 *
	 * @param targetHost The targetHost.
	 */
	public void setTargetHost(String targetHost) {
		this.targetHost = targetHost;
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
