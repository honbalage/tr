/**
 * DialogFactory.java
 */
package org.crf.tr.ui;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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

		// TODONE: wrap it into a StackPane 
		//         add grid on top and the Area to
		//         the bottom
		//         encapsulate the logic into a static method
		//         should the UI controllers remain visible in this scope
		//         return with the Pane to be set in the Dialog
		final GridPane grid = new GridPane( );
		final Label commandLabel = new Label( "Command: " );
		final Label argsLabel = new Label( "Arguments: " );
		final TextField commandField = new TextField( );
		final TextField argsField = new TextField( );
		final TextArea outputArea = new TextArea( );
		
		grid.add( commandLabel, 1, 1 );
		grid.add( commandField, 2, 1 );
		grid.add( argsLabel, 1, 2 );
		grid.add( argsField, 2, 2 );
		//grid.add( outputArea, 1, 3 );
		
		shellDialog.getDialogPane().setContent( grid );

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
				// TODONE: call executor and print out state..
				evt.consume( ); // if not successful
			}
		});
		return shellDialog;
	}
}
