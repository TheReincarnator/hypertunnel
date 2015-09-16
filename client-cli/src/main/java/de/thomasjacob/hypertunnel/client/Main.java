package de.thomasjacob.hypertunnel.client;

import de.thomasjacob.hypertunnel.client.delegate.ClipboardSyncDelegate;
import de.thomasjacob.hypertunnel.client.delegate.FileSyncDelegate;
import de.thomasjacob.hypertunnel.client.delegate.HttpDelegate;
import de.thomasjacob.hypertunnel.client.delegate.IpDelegate;
import de.thomasjacob.hypertunnel.client.tunnel.ClipboardSyncTunnel;
import de.thomasjacob.hypertunnel.client.tunnel.FileSyncTunnel;
import de.thomasjacob.hypertunnel.client.tunnel.IpTunnel;
import de.thomasjacob.hypertunnel.client.tunnel.http.HttpTunnel;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Thomas
 */
public class Main {

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		options.addOption(null, "t", true, null);
		CommandLine commandLine = new DefaultParser().parse(options, args);
		args = commandLine.getArgs();

		HyperTunnelClient client = new HyperTunnelClient();
		client.setId(args[0]);
		client.setServerUrl(args[1]);

		client.addDelegate(new HttpDelegate());
		client.addDelegate(new IpDelegate());
		client.addDelegate(new ClipboardSyncDelegate());
		client.addDelegate(new FileSyncDelegate());

		String[] tunnelOptions = commandLine.getOptionValues("t");
		if (tunnelOptions != null) {
			for (String tunnelString : tunnelOptions) {
				String[] tunnelParts = StringUtils.splitByWholeSeparator(tunnelString, ":");
				String tunnelType = tunnelParts[0];
				String[] parameters = ArrayUtils.subarray(tunnelParts, 1, tunnelParts.length);
				if (tunnelParts.length != 3) {
					if (tunnelType.equals("http")) {
						client.addTunnel(new HttpTunnel(parameters));
					} else if (tunnelType.equals("ip")) {
						client.addTunnel(new IpTunnel(parameters));
					} else if (tunnelType.equals("clipboard")) {
						client.addTunnel(new ClipboardSyncTunnel(parameters));
					} else if (tunnelType.equals("file")) {
						client.addTunnel(new FileSyncTunnel(parameters));
					} else {
						client.addTunnel(new HttpTunnel(parameters));
					}
				}
			}
		}

		client.run();
	}
}
