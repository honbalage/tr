/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;

import org.crf.tr.TestReporter;

import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public class TestResultsTrendView extends VBox implements Viewable {

	public TestResultsTrendView(final TestReporter owner) {
		super( );
		this._owner = owner;
	}
	
	@Override
	public final void refresh() {
		///ELABORATEME:
	}

	public final TestReporter owner() {
		return _owner;
	}

	private final TestReporter _owner;
}
