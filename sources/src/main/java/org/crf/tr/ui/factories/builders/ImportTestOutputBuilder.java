/**
 * ImportTestOutoutBuilder.java
 */
package org.crf.tr.ui.factories.builders;

import static org.crf.tr.ui.factories.builders.NewProjectBuilder.ValueConstraints.isEmpty;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.ui.images.Images;
import org.crf.tr.ui.views.Styles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ImportTestOutputBuilder {

	public static final Optional<Dialog<File>> buildFor(final TestReporter owner) {
		 if (ProjectConstraints.checkNull(owner.currentProject( ))) 
			 return Optional.empty();

		 final Dialog<File> dialog = new Dialog<>( );
		 dialog.setTitle( "Import File" );
		 dialog.setHeaderText( "Choose the latest test output for project " + owner.currentProject().name() + "." );
		 
		 final TextField filepathField = new TextField( );
		 final Pane content = makeContentPane( owner, filepathField );

		 final ButtonType imprt = new ButtonType( "Import", ButtonData.OK_DONE );
		 final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		 final DialogPane dpane = dialog.getDialogPane();
		 dpane.getButtonTypes().addAll( imprt, cancel );
		 dialog.setResultConverter( button -> {
			 if (cancel.equals( button )) return null;
			 return new File(filepathField.getText( ));
		 });
		 dpane.setContent( content );
		 dialog.setGraphic(Images.viewOf( "import-icon.png", 42, 42 ));
		 
		 final Node importBtn = dialog.getDialogPane().lookupButton( imprt );
		 importBtn.addEventFilter( EventType.ROOT, evt -> {
			 if (ActionEvent.ACTION.equals(evt.getEventType( ))) {
				 final String fpath = filepathField.getText( ).trim( );
				 if (isEmpty( fpath, "Test output file path" )) 
					 evt.consume( );
				 else if (FileConstraints.checkExists( fpath ))
					 evt.consume( );
			 }
		 });
		 Styles.applyOn((Button) importBtn);
		 Styles.applyOn((Button) dpane.lookupButton( cancel ));
		 return Optional.of( dialog );
	}

	static final Pane makeContentPane(final TestReporter owner, final TextField field) {
		final HBox pane = new HBox( );
		pane.setSpacing( 7 );
		
		final List<Node> children = pane.getChildren();
		final Label label = new Label( "Test Output:" );
		label.setMinHeight( 24 );
		label.setStyle( "-fx-font-size: 14;" );
		children.add( label );
		field.setMinWidth( 270 );
		children.add( field );

		final Button browse = new Button( "Browse" );
		browse.setOnAction( evt -> {
			final File f = ShellBuilder.fileChooser().showOpenDialog(owner.primary( ));
			if( f == null ) return;
			field.setText(f.getAbsolutePath( ));
		});
		Styles.applyOn( browse );
		children.add( browse );
		return pane;
	}

	private static final class ProjectConstraints {
		static final boolean checkNull(final Project current) {
			if( current == null ) {
				final Alert alert = new Alert( AlertType.ERROR );
				alert.setTitle( "No Project" );
				alert.setHeaderText( "Currently there is no project selected yet." );
				alert.show( );
				return true;
			}
			return false;
		}
	}
	
	private static final class FileConstraints {
		static final boolean checkExists(final String filepath) {
			if (! Files.exists(Paths.get( filepath ))) {
				handleViolation( "File does not exist!" );
				_log.error( "On attempt to import: file \"" + filepath + "\" does not exists!" );
				return true;
			}
			return false;
		}

		static final void handleViolation(final String message) {
			final Alert alert = new Alert( AlertType.ERROR );
			alert.setTitle( "Constraint Violation" );
			alert.setHeaderText( message );
			alert.show( );
		}
	}

	private static final Logger _log = LoggerFactory.getLogger( ImportTestOutputBuilder.class );
}
