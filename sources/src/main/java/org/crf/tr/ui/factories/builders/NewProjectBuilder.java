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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.tools.Strings;
import org.crf.tr.ui.views.Styles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		final TextField projectNameField = new TextField( );
		projectNameField.setMinWidth( 130 );
		final ComboBox<String> frameworkBox = makeComboFor( projectNameField, Project.TestFramework.values( ));
        final Pane content = makeCreateProjectPane( owner, projectNameField, frameworkBox );
        final DialogPane dpane = dialog.getDialogPane();
		dpane.setContent( content );
        
		final ButtonType create = new ButtonType( "Create", ButtonData.OK_DONE );
		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		dpane.getButtonTypes().add( create );
		dpane.getButtonTypes().add( cancel );

		dialog.setResultConverter( button -> {
			if (cancel.equals( button )) return null;
			final String name = projectNameField.getText( );
			final String framework = (String) frameworkBox.getValue( );
            return new Project(name, Project.TestFramework.valueOf( framework ));
		});

		final Node createBtn = dpane.lookupButton( create );
		createBtn.addEventFilter( EventType.ROOT, evt -> {
			if (ActionEvent.ACTION.equals(evt.getEventType( ))) {
				if( ValueConstraints.isEmpty( projectNameField.getText(), "Project Name" ) ||
					ValueConstraints.isEmpty( (String)frameworkBox.getValue(), "Framework" ))
					evt.consume( );
			}
		});
		Styles.applyOn((Button) createBtn);
		Styles.applyOn((Button) dpane.lookupButton( cancel ));
		return dialog;
	}

	static final ComboBox<String> makeComboFor(final TextField nameField, final Project.TestFramework[] values) {
		final ComboBox<String> combo = new ComboBox<>( FXCollections.observableArrayList(
				                                        Arrays.stream(Project.TestFramework.values())
                                                              .map(v -> v.toString( ) )
                                                              .collect(Collectors.toList())));
		combo.setMinWidth(nameField.getMinWidth( ) * 1.5);
		return combo;
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



	public static final class ValueConstraints {
		static final boolean isEmpty(final String field, final String name) {
			if (Strings.isEmpty( field )) {
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
			_log.error( "While creating new project: " + fieldName + " was not given");
			alert.show( );
		}
	}

	private static final Logger _log = LoggerFactory.getLogger( NewProjectBuilder.class );
}
