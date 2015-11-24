/**
 * Styles.java
 */
package org.crf.tr.ui.views;

import org.crf.tr.tools.Strings;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Styles {

	public static final VBox applyForReportView(final VBox view) {
		view.setMinHeight( 660 );
		return applyCommons( view );
	}

	public static final VBox applyForCoverageView(final VBox view) {
		return applyCommons( view );
	}
	
	static final VBox applyCommons(final VBox view) {
		view.setMinWidth( 590 );
		view.setSpacing( 13 );

		final String style = view.getStyle();
		view.setStyle(Strings.isEmpty( style ) ? _commonStyling : ( style + _commonStyling ));
		return view;
	}

   public static final String _commonStyling = "-fx-border-color: deepskyblue; -fx-border-width: 3; -fx-background-color: white; " +
                                               "-fx-effect: dropshadow( gaussian, dimgray, 10, 0.1, 1, 1 )";
}
