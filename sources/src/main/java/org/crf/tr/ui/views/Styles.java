/**
 * Styles.java
 */
package org.crf.tr.ui.views;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Styles {

	public static final VBox applyOnReportView(final VBox view) {
		view.setMinHeight( 770 );
		return applyCommons( view );
	}

	/**
	 * Applies the default <code>Button</code> styling in Test Reporter to the <code>Button</code> instance given.
	 * 
	 * @param button
	 * @return the {@link javafx.scene.control.Button} instance with the extra styling applied
	 * */
	public static final Button applyOn(final Button button) {
		button.getStyleClass().add( _buttonClass );
		return button;
	}

	public static final VBox applyOnCoverageView(final VBox view) {
		return applyCommons( view );
	}

	static final VBox applyCommons(final VBox view) {
		view.setMinWidth( 590 );
		view.setSpacing( 7 );
		view.setPadding(new Insets( 13 ));
		view.getStyleClass().add( _commonsClass );
		return view;
	}

	public static final String _buttonClass = "crf-button";
	public static final String _commonsClass = "crf-commons";
}
