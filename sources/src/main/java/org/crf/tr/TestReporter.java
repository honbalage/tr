/**
 * TestReporter.java
 */
package org.crf.tr;

import static java.lang.System.out;

import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
@SuppressWarnings( "restriction" )
public final class TestReporter extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch( args );
	}


	final Menu makeFileMenu() {
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

	final Menu makeRunMenu() {
		final Menu run = new Menu( "Run" );
		final MenuItem shellCommand = new MenuItem( "Shell Command" );
		shellCommand.setOnAction( evt -> {
			// TODONE: open dialog window..
			out.println( "Executed in shell..." );
		});
		shellCommand.setAccelerator(KeyCombination.keyCombination( "Ctrl+Shift+R" ));

		run.getItems().addAll( shellCommand );
		return run;
	}
	
	/**
	 * */
	final MenuBar makeMenuBar() {
		final MenuBar menuBar = new MenuBar( );
		final Menu fileMenu = makeFileMenu( );
		final Menu runMenu = makeRunMenu( );
		
		menuBar.getMenus().addAll( fileMenu, runMenu );
		return menuBar;
	}

	/**
	 * @param primary
	 * */
	@Override
	public final void start(final Stage primary) {
		final BorderPane main = new BorderPane( );
		main.setTop(makeMenuBar( ));
		
		primary.setScene(new Scene( main, 1024, 768 ));
		primary.show( );
	}
}
