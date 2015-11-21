/**
 * LayoutFactory.java
 */
package org.crf.tr.ui.factories;

import org.crf.tr.TestReporter;

import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class LayoutFactory {

	public static final VBox makeCenterFor(final TestReporter owner) {
		final VBox center = new VBox( );
		return center;
	}
}
