/**
 * NewProjectBuilder.java
 */
package org.crf.tr.ui.factories.builders;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
		dialog.getDialogPane().getButtonTypes().add( create );
		dialog.getDialogPane().getButtonTypes().add( cancel );

		dialog.setResultConverter( button -> {
			if (cancel.equals( button )) return null;
			final String name = projectNameField.getText( );
			final String framework = (String) frameworkBox.getValue( );
            return new Project(name, Project.TestFramework.valueOf( framework ));
		});

		final Node createBtn = dialog.getDialogPane().lookupButton( create );
		createBtn.addEventFilter( EventType.ROOT, evt -> {
			if (ActionEvent.ACTION.equals(evt.getEventType( ))) {
				if( ValueConstraints.isEmpty( projectNameField.getText(), "Project Name" ) ||
					ValueConstraints.isEmpty( (String)frameworkBox.getValue(), "Framework" ))
					evt.consume( );
			}
		});

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



	static final class ValueConstraints {
		static final boolean isEmpty(final String value) {
			return (value == null || value.isEmpty());
		}

		static final boolean isEmpty(final String field, final String name) {
			if (isEmpty( field )) {
				handleNull( name );
				return true;
			}
			return false;
		}

		static final Project checkNull(final String name, final String framework) {
			if (isEmpty( name, "ProjectName" )) return null;
			if (isEmpty( framework, "Framework" )) return null;
			return new Project( name, Project.TestFramework.valueOf( framework ));
		}

		static final void handleNull(final String fieldName) {
			final Alert alert = new Alert( AlertType.ERROR );
			alert.setTitle( "Required Input is Empty" );
			alert.setHeaderText(format( "%s must be given.", fieldName ));
			alert.show( );
		}
	}
}
