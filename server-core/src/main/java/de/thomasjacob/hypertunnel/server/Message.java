package de.thomasjacob.hypertunnel.server;

public class Message {
	private String category;

	private long creationTime = System.currentTimeMillis();
	private byte[] payload;
	private String sourceClient;

	/**
	 * Creates a new Message.
	 *
	 * @param sourceClient
	 * @param category
	 * @param payload
	 */
	public Message(String sourceClient, String category, byte[] payload) {
		this.sourceClient = sourceClient;
		this.category = category;
		this.payload = payload;
	}

	/**
	 * Returns the category.
	 *
	 * @return The category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Returns the creationTime.
	 *
	 * @return The creationTime.
	 */
	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * Returns the payload.
	 *
	 * @return The payload.
	 */
	public byte[] getPayload() {
		return payload;
	}

	/**
	 * Returns the sourceClient.
	 *
	 * @return The sourceClient.
	 */
	public String getSourceClient() {
		return sourceClient;
	}
}