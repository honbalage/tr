/**
 * ProjectHeader.java
 */
package org.crf.tr.ui.views;

import java.util.List;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

		final ProjectHeader header = new ProjectHeader();
		header.setPadding(new Insets( 15, 12, 15, 12 ));
		header.setSpacing( 13 );
		header.setStyle( "-fx-background-color: #336699;" );
		header.getChildren().add( 0, makeStyledLabel( "Project:" ));
		header.getChildren().add( 1, makeStyledLabel(project.toString( ), "-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 16;" ));

		final List<Node> items = center.getChildren();
		if (items.isEmpty())
			items.add( 0, header );
		else 
			items.set( 0, header );
		return header;
	}

	static final Label makeStyledLabel(final String text) {
		return makeStyledLabel( text, "-fx-text-fill: #ffffff; -fx-font-size: 16;" );
	}
	
	static final Label makeStyledLabel(final String text, final String style) {
		final Label label = new Label( text );
		label.setStyle( style );
		return label;
	}
	
	ProjectHeader() {
		super( );
	}
}
