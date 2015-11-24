/**
 * ProjectHeader.java
 */
package org.crf.tr.ui.views;

import java.util.List;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.ui.images.Images;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static java.lang.String.format;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ProjectHeader extends HBox implements Viewable {

	public static final ProjectHeader makeDefaultFor(final TestReporter owner) {
		return new ProjectHeader( );
	}

	public static final ProjectHeader makeFor(final TestReporter owner) {
		final Project project = owner.currentProject( );
		final VBox center = owner.center();

		final ProjectHeader header = Styles.applyOn(new ProjectHeader( ));
		header.setPadding(new Insets( 7, 6, 7, 6 ));
		header.setSpacing( 13 );
		header.getChildren().add( 0, Images.get( "proj-icon.png", 42, 42 ));
		header.getChildren().add( 1, Styles.applyOn(new Label(project.name( )), "crf-project-name-header-label" ));
		final Label l = new Label(format( "[%s]", project.framework().toString( )));
		header.getChildren().add( 2, Styles.applyOn( l, "crf-project-fw-type-header-label" ));

		final List<Node> items = center.getChildren();
		if (items.isEmpty())
			items.add( 0, header );
		else 
			items.set( 0, header );
		return header;
	}

	ProjectHeader() {
		super( );
	}

	@Override
	public final void refresh() {
	}
}
