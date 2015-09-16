package de.thomasjacob.hypertunnel.client.tunnel;

/**
 * @author thomas
 */
public class FileSyncTunnel extends Tunnel {

	private String sourceFolderPath;
	private String targetFolderPath;

	public FileSyncTunnel(String[] parameters) {
		if (parameters.length != 3) {
			throw new IllegalArgumentException("File sync tunnels require 3 parameters (source-path:client:target-path)");
		}

		sourceFolderPath = parameters[0];
		setTargetClient(parameters[1]);
		targetFolderPath = parameters[2];
	}

	/**
	 * Returns the sourceFolderPath.
	 *
	 * @return The sourceFolderPath.
	 */
	public String getSourceFolderPath() {
		return sourceFolderPath;
	}

	/**
	 * Returns the targetFolderPath.
	 *
	 * @return The targetFolderPath.
	 */
	public String getTargetFolderPath() {
		return targetFolderPath;
	}

	/**
	 * Sets the sourceFolderPath.
	 *
	 * @param sourceFolderPath The sourceFolderPath.
	 */
	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}

	/**
	 * Sets the targetFolderPath.
	 *
	 * @param targetFolderPath The targetFolderPath.
	 */
	public void setTargetFolderPath(String targetFolderPath) {
		this.targetFolderPath = targetFolderPath;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

}
