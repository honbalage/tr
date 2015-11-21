/**
 * ViewBuilder.java
 */
package org.crf.tr.ui.views.builders;

import org.crf.tr.TestReporter;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public interface ViewBuilder {

	/**
	 * Builds all the views required for the <code>TestReporter</code> instance and
	 * organizes their layout on the GUI.
	 * 
	 * @param owner
	 * */
	public void buildViewsFor(final TestReporter owner);
}
