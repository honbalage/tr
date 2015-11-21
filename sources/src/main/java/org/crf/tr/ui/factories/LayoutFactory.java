/**
 * LayoutFactory.java
 */
package org.crf.tr.ui.factories;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.ProjectHeader;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class LayoutFactory {

	public static final VBox makeCenterFor(final TestReporter owner) {
		final VBox center = new VBox( );
		center.getChildren().add( 0, ProjectHeader.makeDefaultFor( owner ));
		
		final Image image = new Image( "file:src/main/resources/images/bcg.jpg", owner.width(), owner.height(), false, true );
		final BackgroundImage bcgImage = new BackgroundImage( image
				                                             ,BackgroundRepeat.REPEAT
				                                             ,BackgroundRepeat.NO_REPEAT
				                                             ,BackgroundPosition.DEFAULT
				                                             ,BackgroundSize.DEFAULT);
		center.setBackground(new Background( bcgImage ));
		return center;
	}
}
