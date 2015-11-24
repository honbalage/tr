/**
 * MenuBarFactory.java
 */
package org.crf.tr.ui.factories;

import org.crf.tr.services.factories.Services; 
import org.crf.tr.services.signals.EntityAlreadyExistsException;
import org.crf.tr.ui.images.Images;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert.AlertType;

import org.crf.tr.model.Project;
import org.crf.tr.TestReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class MenuBars {

	static final Menu makeFileMenuFor(final TestReporter owner) {
		final Menu file = new Menu( "File" );
		final MenuItem newProject = new MenuItem("New", Images.get( "add-icon.png" ));
		newProject.setOnAction( evt -> {
			final Dialog<Project> dialog = Dialogs.makeNewProjectFor( owner );
			final Optional<Project> proj = dialog.showAndWait();
			if (! proj.isPresent()) return;
			try {

				owner.currentProject(Services.makeForProjects( ).store(proj.get( )));
			} catch( final EntityAlreadyExistsException e ) {
				handle( e, proj.get() );
			}
		});
		newProject.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+N" ));

		final MenuItem open = new MenuItem("Manage", Images.get( "mng-icon.gif" ));
		open.setOnAction( evt -> {
			final Dialog<Project> dialog = Dialogs.makeManageProjectFor( owner );
			final Optional<Project> proj = dialog.showAndWait();
			if (! proj.isPresent()) return;
			owner.currentProject(proj.get( ));
		});
		open.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+M" ));
		
		final MenuItem save = new MenuItem("Save", Images.get( "save-icon.png" ));
		save.setOnAction( evt -> {
			// TODONE: store to DB
			_log.info( "Test Results saved to db.." );
		});
		save.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+S" ));

		final MenuItem importItem = new MenuItem("Import", Images.get( "import-icon.png" ));
		importItem.setOnAction( evt -> {
			final Optional<Dialog<File>> dialog = Dialogs.makeImportTestOutputFor( owner );
			if (! dialog.isPresent()) return;

			final Optional<File> testOutput = dialog.get( ).showAndWait( );
			if (! testOutput.isPresent()) return;
			owner.process(Paths.get(testOutput.get( ).getAbsolutePath( )));
		});
		importItem.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+I" ));

		final MenuItem exit = new MenuItem("Exit", Images.get( "exit-icon.png" ));
	    exit.setOnAction( evt -> {
	    	// TODONE: setup callbacks..
	    	_log.info( "Exiting Test Reporter.." );
	    	System.exit( 0 );
	    });
	    exit.setAccelerator(KeyCombination.keyCombination( "Ctrl+Q" ));
	    file.getItems().addAll( newProject, open, save, importItem, new SeparatorMenuItem(), exit );
		return file;
	}
	
	static final void handle(final EntityAlreadyExistsException e, final Project p) {
		final Alert alert = new Alert( AlertType.ERROR );
		alert.setTitle( "Project Already Exists" );
		final String message = String.format( "Project with the given name \"%s\" under the given test framework \"%s\" already exists!"
                                             ,p.name()
                                             ,p.framework().toString());
		alert.setHeaderText( message );
		_log.error( "While creating user: " + message );
		alert.show();
	}

	static final Menu makeRunMenuFor(final TestReporter owner) {
		final Menu run = new Menu( "Run" );
		final MenuItem shellCommand = new MenuItem("Shell Command", Images.get( "shell-icon.png" ));
		shellCommand.setOnAction( evt -> {
			final Dialog<String> shellDialog = Dialogs.makeShellFor( owner );
		    shellDialog.show( );
		});
		shellCommand.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+R" ));

		run.getItems().addAll( shellCommand );
		return run;
	}
	
	/**
	 * Produces a <code>MenuBar</code> for (a) TestReporter with the required <code>Menu</code>
	 * elements to control its behavior.
	 * 
	 * @param owner
	 * */
	public static final MenuBar makeFor(final TestReporter owner) {
		final MenuBar menuBar = new MenuBar( );
		final Menu fileMenu = makeFileMenuFor( owner );
		final Menu runMenu = makeRunMenuFor( owner );

		menuBar.getMenus().addAll( fileMenu, runMenu );
		return menuBar;
	}

	private static final Logger _log = LoggerFactory.getLogger( MenuBars.class );
}
