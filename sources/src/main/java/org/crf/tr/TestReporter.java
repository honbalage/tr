/**
 * TestReporter.java
 */
package org.crf.tr;

import static java.lang.System.out;


import org.crf.tr.mongoDB.MongoDBJDBC;


import com.mongodb.DB;
import com.mongodb.DBCollection;

import com.mongodb.DBObject;

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

	static MongoDBJDBC mongoDB = new MongoDBJDBC();
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch( args );
	}


	static final Menu makeFileMenu() {
		final Menu file = new Menu( "File" );
	    final MenuItem exit = new MenuItem( "Exit" );
	    exit.setOnAction( evt -> {
	    	// TODONE: setup callbacks..
	    	System.exit( 0 );
	    });
		return file;
	}

	static final Menu makeRunMenu() {
		final Menu run = new Menu( "Run" );
		final MenuItem shellCommand = new MenuItem( "Shell Command" );
		shellCommand.setOnAction( evt -> {
			// TODONE: use orf.crf.tr.commands.Executor
			out.println( "Executed in shell..." );
		});
		run.getItems().addAll( shellCommand );
		return run;
	}
	
	static final Menu makeMongoDBMenu() {
		final Menu mongo = new Menu( "MongoDB" );
	    final MenuItem test = new MenuItem( "Test" );
	    test.setOnAction( evt -> {
	    	// TODONE: 
	    	DB db = mongoDB.connectToDB("localhost", 27017);
	    	if( mongoDB.authenticateDB(db, "user", "password") ){
	    		DBCollection DBColl = mongoDB.getCollection(db, "coll");
	    		/*DBObject obj = new BasicDBObject("_id", "jo");
	    		mongoDB.insertCollection(DBColl, obj); 
	    		mongoDB.removeCollection(DBColl, obj);*/
	    	}
	    });
	    mongo.getItems().addAll( test );
		return mongo;
	}
	
	/**
	 * */
	static final MenuBar makeMenuBar() {
		final MenuBar menuBar = new MenuBar( );
		final Menu fileMenu = makeFileMenu( );
		final Menu mongoMenu = makeMongoDBMenu();
		final Menu runMenu = makeRunMenu( );
		
		menuBar.getMenus().addAll( fileMenu, mongoMenu, runMenu );
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
