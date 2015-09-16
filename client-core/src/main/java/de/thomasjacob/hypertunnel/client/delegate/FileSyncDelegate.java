package de.thomasjacob.hypertunnel.client.delegate;

/**
 * @author thomas
 */
public class FileSyncDelegate extends Delegate {

	public static final String CATEGORY = "file";

	@Override
	public void execute(String payload) {
	}

	@Override
	public String getCategory() {
		return CATEGORY;
	}

}
