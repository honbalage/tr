/**
 * Styles.java
 */
package org.crf.tr.ui.views;

import static org.crf.tr.tools.Strings.isEmpty;

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
		return applyOn( button, "" );
	}

	/**
	 * Appends the CSS styling described in <code>extraStyling</code> (along with the default <code>Button</code> styling
	 * in Test Reporter) in a manner that it would take precedence over the styling the <code>Button</code> previously had.
	 * 
	 * @param button
	 * @param extraStyle
	 * @return the {@link javafx.scene.control.Button} instance with the extra styling applied
	 * */
	public static final Button applyOn(final Button button, final String extraStyle) {
		final String style = button.getStyle( );
		final String applied = (isEmpty( extraStyle ) ? _buttonStyling : ( _buttonStyling+extraStyle ));
		button.setStyle(isEmpty( style ) ? applied : ( style+applied ));
		return button;
	}

	public static final VBox applyOnCoverageView(final VBox view) {
		return applyCommons( view );
	}

	static final VBox applyCommons(final VBox view) {
		view.setMinWidth( 590 );
		view.setSpacing( 7 );
		view.setPadding(new Insets( 13 ));

		final String style = view.getStyle();
		view.setStyle(isEmpty( style ) ? _commonsStyling : ( style + _commonsStyling ));
		return view;
	}

   public static final String _buttonStyling = "-fx-border-color: deepskyblue; -fx-border-width: 3; -fx-text-fill: lightseagreen; -fx-font-size: 16; " + 
                                               "-fx-font-weight: bold; -fx-background-color: white;";
   public static final String _commonsStyling = "-fx-border-color: deepskyblue; -fx-border-width: 3; -fx-background-color: white; " +
                                                "-fx-effect: dropshadow( gaussian, dimgray, 10, 0.1, 1, 1 )";
}
