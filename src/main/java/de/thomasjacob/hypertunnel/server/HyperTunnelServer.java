package de.thomasjacob.hypertunnel.server;

import org.eclipse.jetty.server.Server;


/**
 * @author Thomas
 */
public class HyperTunnelServer implements Runnable
{
	private int port;

	/**
	 * Creates a new Server.
	 * 
	 * @param port
	 */
	public HyperTunnelServer(int port)
	{
		this.port = port;
	}

	public void run()
	{
		Server server = new Server(port);
		server.setHandler(new HyperTunnelHandler());

		try
		{
			server.start();
			server.join();
		}
		catch (Exception exception)
		{
			throw new RuntimeException("Failed to start hypertunnel server", exception);
		}
	}
}
