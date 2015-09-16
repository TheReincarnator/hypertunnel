package de.thomasjacob.hypertunnel.server;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Thomas
 */
public class Main {
	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		CommandLine commandLine = new DefaultParser().parse(options, args);
		args = commandLine.getArgs();

		String type = args.length > 0 ? args[1] : "";
		if (args.length == 1) {
			int port = Integer.parseInt(args[0]);
			new HyperTunnelServer(port).run();
		}
	}
}
