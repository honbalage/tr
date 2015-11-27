/**
 * Styles.java
 */
package org.crf.tr.ui.views;


import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Styles {

	public static final String pathOf(final String cssFile) {
		return String.format( _cssResourcePathFormat, cssFile );
	}

	public static final VBox applyOnReportView(final VBox view) {
		applyOn( view, "crf-report-view" );
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

	public static final ProjectHeader applyOn(final ProjectHeader header) {
		header.getStyleClass().add( _projectHeaderClass );
		return header;
	}

	public static final <T extends Node> T applyOn(final T node, final String cssClass) {
		node.getStyleClass().add( cssClass );
		return node;
	}

	public static final <T extends Node> T applyOn(final T node, final String... cssClasses) {
		node.getStyleClass().addAll( cssClasses );
		return node;
	}

	public static final VBox applyOnCoverageView(final VBox view) {
		return applyCommons( view );
	}

	public static final VBox applyCommons(final VBox view) {
		view.setSpacing( 7 );
		view.setPadding(new Insets( 13 ));
		view.getStyleClass().add( _commonsClass );
		return view;
	}


	public static final String projectHeaderClass() {
		return _projectHeaderClass;
	}

	public static final String buttonClass() {
		return _buttonClass;
	}

	public static final String commonsClass() {
		return _commonsClass;
	}


	private static final String _cssResourcePathFormat = "file:src/main/resources/css/%s";
	private static final String _projectHeaderClass = "crf-project-header";
	private static final String _buttonClass = "crf-button";
	private static final String _commonsClass = "crf-commons";
}
