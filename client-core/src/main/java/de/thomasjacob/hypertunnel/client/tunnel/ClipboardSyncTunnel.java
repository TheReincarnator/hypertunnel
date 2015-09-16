package de.thomasjacob.hypertunnel.client.tunnel;

import de.thomasjacob.hypertunnel.client.delegate.ClipboardSyncDelegate;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * @author thomas
 */
public class ClipboardSyncTunnel extends Tunnel {

	private String lastContent;
	private int period = 250;
	private WorkerThread workerThread;

	public ClipboardSyncTunnel(String[] parameters) {
		if (parameters.length != 1) {
			throw new IllegalArgumentException("Clipboard tunnels require the target client as parameter");
		}

		setTargetClient(parameters[0]);

		checkClipboard();
	}

	private boolean checkClipboard() {
		String currentContent = getClipboardContent();
		if (currentContent == null) {
			return false;
		}

		boolean changed = !currentContent.equals(lastContent);
		if (changed) {
			System.out.println("Clipboard changed");
		}

		lastContent = currentContent;

		return changed;
	}

	private String getClipboardContent() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(null);
		if (content == null || !content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return null;
		}

		try {
			return (String) content.getTransferData(DataFlavor.stringFlavor);
		} catch (Exception exception) {
			// Ignored
			return null;
		}
	}

	/**
	 * Returns the period.
	 *
	 * @return The period.
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 *
	 * @param period The period.
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	@Override
	public synchronized void start() {
		if (workerThread != null) {
			return;
		}

		workerThread = new WorkerThread();
		workerThread.start();
	}

	@Override
	public synchronized void stop() {
		if (workerThread == null) {
			return;
		}

		workerThread.shutdown();
		workerThread = null;
	}

	public void synchronizeClipboard(boolean force) {
		boolean changed = checkClipboard();
		if (changed || force) {
			getSourceClient().sendMessage(getTargetClient(), ClipboardSyncDelegate.CATEGORY, lastContent);
		}
	}

	private class WorkerThread extends Thread {
		private boolean running = true;

		@Override
		public synchronized void run() {
			while (running) {
				synchronizeClipboard(false);
				try {
					wait(period);
				} catch (InterruptedException exception) {
					// Ignored
				}
			}
		}

		public synchronized void shutdown() {
			running = false;
			notifyAll();
		}
	}
}
