/**
 * LayoutFactory.java
 */
package org.crf.tr.ui.factories;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.images.Images;
import org.crf.tr.ui.views.ProjectHeader;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
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

		final ImageView iview = Images.get("bcg.jpg", owner.width(), owner.height( ));
		iview.setOpacity( 0.01 );
		final BackgroundImage bcgImage = new BackgroundImage( iview.getImage()
				                                             ,BackgroundRepeat.NO_REPEAT
				                                             ,BackgroundRepeat.NO_REPEAT
				                                             ,BackgroundPosition.DEFAULT
				                                             ,BackgroundSize.DEFAULT);
		center.setBackground(new Background( bcgImage ));
		return center;
	}

	public static final HBox makeContainerFor(final TestReporter owner) {
		final HBox container = new HBox( );
		container.setPadding(new Insets( 13, 7, 7, 7 ));
		final VBox center = owner.center();
		center.getChildren().add( 1, container );
		return container;
	}
}
