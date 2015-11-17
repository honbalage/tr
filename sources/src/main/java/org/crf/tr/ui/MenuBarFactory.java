/**
 * MenuBarFactory.java
 */
package org.crf.tr.ui;


import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Dialog;
import static java.lang.System.out;
import javafx.scene.control.Menu;

import org.crf.tr.TestReporter;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class MenuBarFactory {

	static final Menu makeFileMenuFor(final TestReporter owner) {
		final Menu file = new Menu( "File" );
		final MenuItem open = new MenuItem( "Open" );
		open.setOnAction( evt -> {
			// TODONE: call file chooser && results opener
			out.println( "Test Results opened.." );
		});
		open.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+O" ));
		
		final MenuItem archive = new MenuItem( "Archive" );
		archive.setOnAction( evt -> {
			// TODONE: store to DB
			out.println( "Test Results saved to db.." );
		});
		archive.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+S" ));
	    
		final MenuItem exit = new MenuItem( "Exit" );
	    exit.setOnAction( evt -> {
	    	// TODONE: setup callbacks..
	    	System.exit( 0 );
	    });
	    exit.setAccelerator(KeyCombination.keyCombination( "Ctrl+Q" ));
	    file.getItems().addAll( open, archive, new SeparatorMenuItem(), exit );
		return file;
	}

	static final Menu makeRunMenuFor(final TestReporter owner) {
		final Menu run = new Menu( "Run" );
		final MenuItem shellCommand = new MenuItem( "Shell Command" );
		shellCommand.setOnAction( evt -> {
			final Dialog<String> shellDialog = DialogFactory.makeShellFor( owner );
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

}
