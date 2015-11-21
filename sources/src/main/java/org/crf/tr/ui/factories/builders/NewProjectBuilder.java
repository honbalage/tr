/**
 * NewProjectBuilder.java
 */
package org.crf.tr.ui.factories.builders;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class NewProjectBuilder {

	public static final Dialog<Project> buildFor(final TestReporter owner) {
		final Dialog<Project> dialog = new Dialog<>( );
		dialog.setTitle( "Create New Project" );
		dialog.initModality( Modality.APPLICATION_MODAL );
		dialog.setResizable( true );

		final TextField projectNameField = new TextField( );
		final ComboBox<String> frameworkBox = makeComboFor(Project.TestFramework.values( ));
        final Pane content = makeCreateProjectPane( owner, projectNameField, frameworkBox );
		dialog.getDialogPane().setContent( content );
        
		final ButtonType create = new ButtonType( "Create", ButtonData.OK_DONE );
		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		dialog.setResultConverter( button -> {
			if (cancel.equals( button )) return null;

			return new Project( projectNameField.getText( )
					           ,Project.TestFramework.valueOf((String) frameworkBox.getValue( )));
		});
		dialog.getDialogPane().getButtonTypes().add( create );
		dialog.getDialogPane().getButtonTypes().add( cancel );
		return dialog;
	}

	static final ComboBox<String> makeComboFor(final Project.TestFramework[] values) {
		return new ComboBox<>(FXCollections.observableArrayList(
				                              Arrays.stream(Project.TestFramework.values())
                                                    .map(v -> v.toString( ) )
                                                    .collect(Collectors.toList())));
	}

	static final Pane makeCreateProjectPane(final TestReporter owner, final Node... nodes) {
		final GridPane grid = new GridPane();
		grid.setVgap( 3.1 );
		grid.setHgap( 3.1 );

		grid.add( new Label("Project Name: "), 1, 1 );
		grid.add( nodes[0], 2, 1 );
		grid.add( new Label("Framework Type: "), 1, 2 );
		grid.add( nodes[1], 2, 2 );
		return grid;
	}
}
