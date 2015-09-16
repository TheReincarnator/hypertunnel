package de.thomasjacob.hypertunnel.client.delegate;

/**
 * @author thomas
 */
public class IpDelegate extends Delegate {

	public static final String CATEGORY = "ip";

	@Override
	public void execute(String sourceClient, byte[] payload) {
	}

	@Override
	public String getCategory() {
		return CATEGORY;
	}
}
