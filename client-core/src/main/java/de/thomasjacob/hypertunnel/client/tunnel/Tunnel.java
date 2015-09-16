package de.thomasjacob.hypertunnel.client.tunnel;

import de.thomasjacob.hypertunnel.client.HyperTunnelClient;

/**
 * @author thomas
 */
public abstract class Tunnel {

	private String password;
	private HyperTunnelClient sourceClient;
	private String targetClient;

	/**
	 * Returns the password.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the sourceClient.
	 *
	 * @return The sourceClient.
	 */
	protected HyperTunnelClient getSourceClient() {
		return sourceClient;
	}

	/**
	 * Returns the targetClient.
	 *
	 * @return The targetClient.
	 */
	public String getTargetClient() {
		return targetClient;
	}

	/**
	 * Sets the password.
	 *
	 * @param password The password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void setSourceClient(HyperTunnelClient sourceClient) {
		this.sourceClient = sourceClient;
	}

	/**
	 * Sets the targetClient.
	 *
	 * @param targetClient The targetClient.
	 */
	public void setTargetClient(String targetClient) {
		this.targetClient = targetClient;
	}

	public abstract void start();

	public abstract void stop();
}
