/**
 * TestReporter.java
 */
package org.crf.tr;

import org.crf.tr.ui.MenuBarFactory;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class TestReporter extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch( args );
	}

	/**
	 * @param primary
	 * */
	@Override
	public final void start(final Stage primary) {
		final BorderPane main = new BorderPane( );
		main.setTop(MenuBarFactory.makeFor( this ));
		
		primary.setScene(new Scene( main, 1024, 768 ));
		primary.show( );
	}
}
