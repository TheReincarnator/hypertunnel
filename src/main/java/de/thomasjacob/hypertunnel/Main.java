package de.thomasjacob.hypertunnel;

import de.thomasjacob.hypertunnel.client.HyperTunnelClient;
import de.thomasjacob.hypertunnel.server.HyperTunnelServer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Thomas
 */
public class Main
{
	public static void main(String[] args) throws ParseException
	{
		Options options = new Options();
		CommandLine commandLine = new DefaultParser().parse(options, args);
		args = commandLine.getArgs();

		String type = args.length > 0 ? args[1] : "";
		if (type.equals("server"))
		{
			if (args.length == 2)
			{
				int port = Integer.parseInt(args[1]);
				new HyperTunnelServer(port).run();
			}
		}
		else if (type.equals("client"))
		{
			if (args.length == 3)
			{
				String host = args[1];
				int port = Integer.parseInt(args[2]);
				HyperTunnelClient client = new HyperTunnelClient(host, port);

				for (String tunnelString : commandLine.getOptionValues("L"))
				{
					String[] tunnelParts = StringUtils.splitByWholeSeparator(tunnelString, ":");
					if (tunnelParts.length != 3)
					{

					}
				}

				client.run();
			}
		}
	}
}
