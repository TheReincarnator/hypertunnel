package de.thomasjacob.hypertunnel.client.delegate;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author thomas
 */
public class ClipboardSyncDelegate extends Delegate implements ClipboardOwner {

	public static final String CATEGORY = "clipboard";

	@Override
	public void execute(String sourceClient, String payload) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(payload), this);
	}

	@Override
	public String getCategory() {
		return CATEGORY;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// Do nothing
	}
}
