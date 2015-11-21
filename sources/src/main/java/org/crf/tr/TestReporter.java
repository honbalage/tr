/**
 * TestReporter.java
 */
package org.crf.tr;

import org.crf.tr.model.Project;


//import org.crf.tr.mongoDB.MongoDBJDBC;


//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//
//import com.mongodb.DBObject;


import org.crf.tr.ui.factories.LayoutFactory;
import org.crf.tr.ui.factories.MenuBarFactory;
import org.crf.tr.ui.views.ProjectHeader;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
		this._primary = primary;
		final BorderPane main = new BorderPane( );
		main.setTop(MenuBarFactory.makeFor( this ));
		_center = LayoutFactory.makeCenterFor( this );
		main.setCenter( _center );
		
		primary.setTitle( "Test Reporter" );
		primary.getIcons().add(new Image( "file:src/main/resources/images/tr-icon.png" ));
		primary.setScene(new Scene(main, width(), height( )));
		primary.show( );
	}

	public final Stage primary() {
		return this._primary;
	}

	public final Project currentProject() {
		return _currentProject;
	}
	
	public final void currentProject(final Project project) {
		this._currentProject = project;
		ProjectHeader.makeFor( this );
		project.framework().viewBuilder().buildViewsFor( this );
	}
	
	public final VBox center() {
		return _center;
	}

	public final int width() {
		return _width;
	}

	public final int height() {
		return _height;
	}

	private final int _width  = 1280;
	private final int _height = 768;

	private VBox _center;
	private Stage _primary;
	private Project _currentProject;
}
