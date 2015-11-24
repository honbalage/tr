/**
 * LayoutFactory.java
 */
package org.crf.tr.ui.factories;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.ProjectHeader;
import org.crf.tr.ui.views.Styles;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Layouts {

	public static final VBox makeCenterFor(final TestReporter owner) {
		final VBox center = new VBox( );
		center.getChildren().add( 0, ProjectHeader.makeDefaultFor( owner ));
		Styles.applyOn( center, "crf-tr-center" );
		return center;
	}

	public static final HBox makeContainerFor(final TestReporter owner) {
		final HBox container = new HBox( );
		container.setPadding(new Insets( 7, 7, 7, 7 ));
		final VBox center = owner.center();
		center.getChildren().add( 1, container );
		return container;
	}
}
