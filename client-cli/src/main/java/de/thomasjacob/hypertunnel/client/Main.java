package de.thomasjacob.hypertunnel.client;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Thomas
 */
public class Main {

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		CommandLine commandLine = new DefaultParser().parse(options, args);
		args = commandLine.getArgs();

		if (args.length == 1) {
			String url = args[0];

			HyperTunnelClient client = new HyperTunnelClient();
			client.setServerUrl(url);

			for (String tunnelString : commandLine.getOptionValues("L")) {
				String[] tunnelParts = StringUtils.splitByWholeSeparator(tunnelString, ":");
				if (tunnelParts.length != 3) {

				}
			}

			client.run();
		}
	}
}
