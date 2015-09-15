package de.thomasjacob.hypertunnel.client;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Thomas
 */
public class HyperTunnelClient implements Runnable
{
	private String host;

	private int port;

	private List<Tunnel> tunnels = new ArrayList<Tunnel>();

	/**
	 * Creates a new Client.
	 * 
	 * @param host
	 * @param port
	 */
	public HyperTunnelClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void addTunnel(int sourcePort, String targetHost, int targetPort)
	{
		tunnels.add(new Tunnel(sourcePort, targetHost, targetPort));
	}

	public void run()
	{
	}
}
