/**
 * DialogFactory.java
 */
package org.crf.tr.ui;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.util.stream.Collectors;

import org.crf.tr.TestReporter;
import org.crf.tr.commands.Executor;
import org.crf.tr.model.Project;
import org.crf.tr.services.factories.ServiceFactory;

import javafx.stage.Modality;
import javafx.scene.Node;

import java.util.Arrays;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
///REFINEME: create separate builder classes for each public makeXxx() function ;)
public final class DialogFactory {

	private static final FileChooser _fileChooser = new FileChooser( );
	
	public static final Dialog<Project> makeOpenProjectFor(final TestReporter owner) {
		final Dialog<Project> dialog = new Dialog<>( );
		dialog.setTitle( "Open Project" );
		
		///ELABORATEME: ServiceFactory.makeForProjects().listAll( );
		///             then create a...
		final List<Project> projects = ServiceFactory.makeForProjects().listAll( );
		final ListView<Project> projectsView = new ListView<>(FXCollections.observableArrayList( projects ));
		projectsView.setEditable( false );
		final Pane content = makeOpenProjectPane( owner, projectsView );
		
		final ButtonType open = new ButtonType( "Open", ButtonData.OK_DONE );
		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		dialog.getDialogPane().getButtonTypes().add( open );
		dialog.getDialogPane().getButtonTypes().add( cancel );
		dialog.setResultConverter( button -> {
			if (cancel.equals( button )) return null;
			return projectsView.getSelectionModel().getSelectedItem();
		});
		dialog.getDialogPane().setContent( content );
		return dialog;
	}
	
	static final Pane makeOpenProjectPane(final TestReporter owner, final Node... nodes) {
		final StackPane stack = new StackPane( );
		stack.getChildren().add( nodes[0] );
		return stack;
	}
	
	public static final Dialog<Project> makeNewProjectFor(final TestReporter owner) {
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
	
	static final ComboBox<String> makeComboFor(final Project.TestFramework[] values) {
		return new ComboBox<>(FXCollections.observableArrayList(
				                              Arrays.stream(Project.TestFramework.values())
                                                    .map(v -> v.toString( ) )
                                                    .collect(Collectors.toList())));
	}
	
	public static final Dialog<String> makeShellFor(final TestReporter owner) {
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
		shellDialog.setResultConverter( btype -> {
			return "";
		});

		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		shellDialog.getDialogPane().getButtonTypes().add( 0, exec );
		shellDialog.getDialogPane().getButtonTypes().add( 1, cancel );
		
		final Node execButton = shellDialog.getDialogPane().lookupButton( exec );
		execButton.addEventFilter(EventType.ROOT, evt -> {
			if( evt.getEventType().equals( ActionEvent.ACTION )) {
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
			final File f = _fileChooser.showOpenDialog(owner.primary( ));
			if( f == null ) return;
			commandField.setText(f.getAbsolutePath( ));
		});
		browse.setPrefWidth( 77 );
		return browse;
	}
	
	static final Button makeArgsBrowserButton(final TextField argsField, final TestReporter owner) {
		final Button browse = new Button( "Browse" );
		browse.setOnAction( evt -> {
			final File f = _fileChooser.showOpenDialog(owner.primary( ));
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
		alert.setHeaderText( "Message: " + e.getMessage() );
		alert.setContentText("While reading from: " + f.getAbsolutePath());
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
}
