/**
 * DialogFactory.java
 */
package org.crf.tr.ui;

import javafx.scene.layout.ColumnConstraints;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import org.crf.tr.TestReporter;

import javafx.stage.Modality;
import javafx.scene.Node;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class DialogFactory {

	public static final Dialog<String> makeShellFor(final TestReporter owner) {
		final Dialog<String> shellDialog = new Dialog<>( );
		shellDialog.setTitle( "Shell Command Input" );
		shellDialog.setHeaderText( "Insert command and arguments to be executed." );
		shellDialog.setResizable( true );
		shellDialog.initModality( Modality.APPLICATION_MODAL );

		final TextField commandField = makeCommandField( owner );
		final TextField argsField = makeCommandField( owner );		
		final TextArea outputArea = makeOutputArea( owner );

		final Pane commandPane = makeCommandPane( commandField, argsField, outputArea );
		shellDialog.getDialogPane().setContent( commandPane );

		final ButtonType exec = new ButtonType( "Execute", ButtonData.OK_DONE );
		shellDialog.setResultConverter( btype -> {
			return "";
		});

		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		shellDialog.getDialogPane().getButtonTypes().add( 0, exec );
		shellDialog.getDialogPane().getButtonTypes().add( 1, cancel );
		
		final Node execButton = shellDialog.getDialogPane().lookupButton( exec );
		execButton.addEventFilter(EventType.ROOT, evt -> {
			if( evt.getEventType().equals( ActionEvent.ACTION )) {
				final String command = CommandConstraints.handleEmpty( commandField, evt );
				final String args = argsField.getText( );
				// TODONE: execute command and args..
			}
		});
		return shellDialog;
	}
	
	static final class CommandConstraints {
		static final String handleEmpty(final TextField commandField, final Event evt) {
			final String command = commandField.getText().trim();
			if (command.isEmpty( )) {
				final PseudoClass error = PseudoClass.getPseudoClass( "error" );
				commandField.pseudoClassStateChanged( error,  true );

				final Alert alert = new Alert( AlertType.ERROR, "Empty Command" );
				alert.setTitle( "Empty Command" );
				alert.setHeaderText( "Command not given." );
				alert.setContentText( "Command must be given to be executed." );
				alert.show( );
				evt.consume( );
			}
			return command;
		}
	}
	
	static final TextArea makeOutputArea(final TestReporter owner) {
		final TextArea outputArea = new TextArea( );
		outputArea.setEditable( false );
		return outputArea;
	}
	
	static final TextField makeCommandField(final TestReporter owner) {
		final TextField field = new TextField( );
		field.setPrefWidth( 450 );
		GridPane.setHgrow( field, Priority.ALWAYS );
		GridPane.setFillWidth( field, true );
		GridPane.setHalignment( field , HPos.LEFT );
		return field;
	}

	static final Pane makeCommandPane(final Node... nodes) {
		final GridPane grid = new GridPane( );
		grid.setVgap( 3.1 );
		
		final ColumnConstraints col1 = new ColumnConstraints( );
		final ColumnConstraints col2 = new ColumnConstraints( );
		col2.setHgrow( Priority.ALWAYS );
		grid.getColumnConstraints().add( 0, col1 );
		grid.getColumnConstraints().add( 1, col2 );
		
		final Label commandLabel = new Label( "Command: " );
		final Label argsLabel = new Label( "Arguments: " );		
		
		grid.add( commandLabel, 1, 1 );
		grid.add( nodes[0], 2, 1 );
		grid.add( argsLabel, 1, 2 );
		grid.add( nodes[1], 2, 2 );
		
		final VBox commandPane = new VBox( );
		commandPane.setSpacing( 6.3 );
	    commandPane.getChildren().addAll( grid, nodes[2] );
		return commandPane;
	}
}
