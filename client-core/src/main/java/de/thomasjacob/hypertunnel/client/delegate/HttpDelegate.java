package de.thomasjacob.hypertunnel.client.delegate;

/**
 * @author thomas
 */
public class HttpDelegate extends Delegate {

	public static final String CATEGORY = "http";

	@Override
	public void execute(String payload) {
	}

	@Override
	public String getCategory() {
		return CATEGORY;
	}

}
