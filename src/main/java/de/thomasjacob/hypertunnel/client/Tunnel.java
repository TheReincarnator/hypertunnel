package de.thomasjacob.hypertunnel.client;

/**
 * @author Thomas
 */
public class Tunnel
{
	private int sourcePort;

	private String targetHost;

	private int targetPort;

	/**
	 * Creates a new Tunnel.
	 * 
	 * @param host
	 * @param port
	 */
	public Tunnel(int sourcePort, String targetHost, int targetPort)
	{
		this.sourcePort = sourcePort;
		this.targetHost = targetHost;
		this.targetPort = targetPort;
	}

	public int getSourcePort()
	{
		return sourcePort;
	}

	public String getTargetHost()
	{
		return targetHost;
	}

	public int getTargetPort()
	{
		return targetPort;
	}
}
