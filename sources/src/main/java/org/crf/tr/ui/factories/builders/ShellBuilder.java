/**
 * ShellBuilder.java
 */
package org.crf.tr.ui.factories.builders;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import org.crf.tr.TestReporter;
import org.crf.tr.commands.Executor;
import org.crf.tr.ui.images.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ShellBuilder {

	static final FileChooser fileChooser() {
		return FileChooserHolder._fileChooser;
	}

	public static final Dialog<String> buildFor(final TestReporter owner) {
		final Dialog<String> shellDialog = new Dialog<>( );
		shellDialog.setTitle( "Shell Command Input" );
		shellDialog.setHeaderText( "Insert command and arguments to be executed." );
		shellDialog.setResizable( true );
		shellDialog.initModality( Modality.APPLICATION_MODAL );

		final TextField commandField = makeCommandField( owner );
		final TextField argsField = makeCommandField( owner );		
		final TextArea outputArea = makeOutputArea( owner );

		final Pane commandPane = makeCommandPane( owner, commandField, argsField, outputArea );
		shellDialog.getDialogPane().setContent( commandPane );

		final ButtonType exec = new ButtonType( "Execute", ButtonData.OK_DONE );
		shellDialog.setResultConverter( btype -> "" );

		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		shellDialog.getDialogPane().getButtonTypes().add( 0, exec );
		shellDialog.getDialogPane().getButtonTypes().add( 1, cancel );
		
		final Node execButton = shellDialog.getDialogPane().lookupButton( exec );
		execButton.addEventFilter(EventType.ROOT, evt -> {
			if (ActionEvent.ACTION.equals(evt.getEventType( ))) {
				final String cmd = CommandConstraints.handleEmpty( commandField, evt );
				final List<String> command = Arrays.stream(argsField.getText().split( " " ))
						                           .filter(s -> !s.trim().isEmpty( ))
						                           .collect(Collectors.toList( ));
                command.add( 0, cmd );
		        final int exitStatus = Executor.inShell( command, p -> {
		        	final InputStream stdout = p.getInputStream();
		        	readAllInto( outputArea, stdout );
		        });
		        handleNonZero( exitStatus );
				evt.consume( );
			}
		});
		shellDialog.setGraphic(Images.viewOf( "shell-icon.png", 42, 42 ));
		return shellDialog;
	}

	static final void readAllInto(final TextArea out, final InputStream stdout) {
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader( stdout ))){
			
			String line;
			while ((line = reader.readLine()) != null) {
				out.appendText( line );
				out.appendText( "\n" );
			}
		} catch( final IOException e ) {
			handle( e, "reading from command output" );
		}
	}
	
	static final void handleNonZero(final int exitStatus) {
		if( exitStatus != 0 ) {
			final Alert alert = new Alert( AlertType.ERROR, "Non Zero Exit Status" );
			alert.setTitle( "Non Zero Exit Status" );
			alert.setHeaderText( "Process returned non-zero exit status." );
			alert.setContentText( "Please see output log." );
			alert.show( );
		}
	}

	
	static final void handle(final Exception e, final String location) {
		final Alert alert = new Alert( AlertType.ERROR, "General Error" );
		alert.setTitle( "General Error" );
		alert.setHeaderText(format( "Error in %s.", location ));
		alert.setContentText( "Message: " + e.getMessage() );
		alert.show( );
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
		field.setPrefWidth( 310 );
		GridPane.setHgrow( field, Priority.ALWAYS );
		GridPane.setFillWidth( field, true );
		GridPane.setHalignment( field , HPos.LEFT );
		return field;
	}

	static final Button makeCommandBrowserButton(final TextField commandField, final TestReporter owner) {
		final Button browse = new Button( "Browse" );
		browse.setOnAction( evt -> {
			final File f = fileChooser().showOpenDialog(owner.primary( ));
			if( f == null ) return;
			commandField.setText(f.getAbsolutePath( ));
		});
		browse.setPrefWidth( 77 );
		return browse;
	}
	
	static final Button makeArgsBrowserButton(final TextField argsField, final TestReporter owner) {
		final Button browse = new Button( "Browse" );
		browse.setOnAction( evt -> {
			final File f = fileChooser().showOpenDialog(owner.primary( ));
			if( f == null ) return;

			final StringBuilder builder = new StringBuilder( );
			try {
			    Files.readAllLines(Paths.get(f.getAbsolutePath())).forEach( l -> {
			    	builder.append( l );
			    	builder.append( " " );
			    });
			    argsField.setText(builder.toString( ));
			} catch( final IOException e ) {
				handle( e, f );
			}
		});
		browse.setPrefWidth( 77 );
		return browse;
	}
	
	static final void handle(final IOException e, final File f) {
		final Alert alert = new Alert( AlertType.ERROR, "IO Error" );
		alert.setTitle( "IO Error" );
		final String message = e.getMessage( );
		alert.setHeaderText( "Message: " + message );
		final String abspath = f.getAbsolutePath();
		alert.setContentText("While reading from: " + abspath);
		_log.error(format( "IOError[\"%s\"]: %s", abspath, message ));
		alert.show( );
	}

	static final Pane makeCommandPane(final TestReporter owner, final Node... nodes) {
		final GridPane grid = new GridPane( );
		grid.setVgap( 3.1 );
		grid.setHgap( 3.1 );

		final Label commandLabel = new Label( "Command: " );
		final Label argsLabel = new Label( "Arguments: " );		
		
		grid.add( commandLabel, 1, 1 );
		grid.add( nodes[0], 2, 1 );
		grid.add( makeCommandBrowserButton((TextField) nodes[0], owner ), 3, 1 );
		grid.add( argsLabel, 1, 2 );
		grid.add( nodes[1], 2, 2 );
		grid.add( makeArgsBrowserButton((TextField) nodes[1], owner ), 3, 2 );
		
		final VBox commandPane = new VBox( );
		commandPane.setSpacing( 6.3 );
	    commandPane.getChildren().addAll( grid, nodes[2] );
		return commandPane;
	}

	private static final class FileChooserHolder {
		static final FileChooser _fileChooser = new FileChooser( );
	}

	private static final Logger _log = LoggerFactory.getLogger( ShellBuilder.class );
}
