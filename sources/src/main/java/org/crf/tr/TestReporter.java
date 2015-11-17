/**
 * TestReporter.java
 */
package org.crf.tr;

import org.crf.tr.ui.MenuBarFactory;


//import org.crf.tr.mongoDB.MongoDBJDBC;


//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//
//import com.mongodb.DBObject;

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

//	static MongoDBJDBC mongoDB = new MongoDBJDBC();
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch( args );
	}

//	static final Menu makeMongoDBMenu() {
//		final Menu mongo = new Menu( "MongoDB" );
//	    final MenuItem test = new MenuItem( "Test" );
//	    test.setOnAction( evt -> {
//	    	// TODONE: 
//	    	DB db = mongoDB.connectToDB("localhost", 27017);
//	    	if( mongoDB.authenticateDB(db, "user", "password") ){
//	    		DBCollection DBColl = mongoDB.getCollection(db, "coll");
//	    		/*DBObject obj = new BasicDBObject("_id", "jo");
//	    		mongoDB.insertCollection(DBColl, obj); 
//	    		mongoDB.removeCollection(DBColl, obj);*/
//	    	}
//	    });
//	    mongo.getItems().addAll( test );
//		return mongo;
//	}
	
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
