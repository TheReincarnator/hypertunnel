package de.thomasjacob.hypertunnel.server;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.server.Server;

/**
 * @author Thomas
 */
public class Main {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		CommandLine commandLine = new DefaultParser().parse(options, args);
		args = commandLine.getArgs();

		if (args.length == 1) {
			int port = Integer.parseInt(args[0]);
			HyperTunnelServer hyperTunnelServer = new HyperTunnelServer();

			Server server = new Server(port);
			server.setHandler(new HyperTunnelHandler(hyperTunnelServer));

			try {
				server.start();
				server.join();
			} catch (Exception exception) {
				throw new RuntimeException("Failed to start hypertunnel server", exception);
			}
		}
	}
}
