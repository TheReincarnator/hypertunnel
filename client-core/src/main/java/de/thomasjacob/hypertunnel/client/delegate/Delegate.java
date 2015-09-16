package de.thomasjacob.hypertunnel.client.delegate;

/**
 * @author thomas
 */
public abstract class Delegate {

	public abstract void execute(String sourceClient, String payload);

	public abstract String getCategory();
}
