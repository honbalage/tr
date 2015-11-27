/**
 * TestReporter.java
 */
package org.crf.tr;

import java.nio.file.Path;
import java.util.List;

import org.crf.tr.model.Project;
import org.crf.tr.mongoDB.MongoDBJDBC;
import org.crf.tr.services.factories.Services;
import org.crf.tr.services.signals.EntityAlreadyExistsException;
import org.crf.tr.ui.factories.Layouts;
import org.crf.tr.ui.factories.MenuBars;
import org.crf.tr.ui.images.Images;
import org.crf.tr.ui.views.ProjectHeader;
import org.crf.tr.ui.views.Styles;
import org.crf.tr.ui.views.Viewable;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
		MongoDBJDBC.startMongo();
		
		launch( args );

	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            MongoDBJDBC.finishMongo();
	        }
	    }, "Shutdown Mongo thread"));
	}

	/**
	 * @param primary
	 * */
	@Override
	public final void start(final Stage primary) {
		this._primary = primary;
		final BorderPane main = new BorderPane( );
		main.setTop(MenuBars.makeFor( this ));
		_center = Layouts.makeCenterFor( this );
		_container = Layouts.makeContainerFor( this );
		main.setCenter( _center );
		
		primary.setTitle( "Test Reporter" );
		primary.getIcons().add(Images.get( "tr-icon.png" ).getImage( ));
		final Scene mainScene = new Scene(main, width(), height( ));
		mainScene.getStylesheets().addAll(Styles.pathOf( "tr-views.css" ), Styles.pathOf( "tr-commons.css" ));
		primary.setScene( mainScene );

		///TODONE: remove
		setupTestProject( this );
		primary.show( );
	}

	///TODONE: remove
	private static final void setupTestProject(final TestReporter tr) {
		final Project p = new Project( "First", Project.TestFramework.valueOf( "Clover" ));
		try {
			Services.makeForProjects().store( p );
		} catch (EntityAlreadyExistsException e) {
		}
		tr.currentProject( p );
		_log.debug( "Project " + p.toString() + " created." );
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

	public final HBox container() {
		return _container;
	}

	public final int width() {
		return _width;
	}

	public final int height() {
		return _height;
	}

	public final void process(final Path testOutput) {
		///ELABORATEME: for( view : views ) view->refresh( );
		_log.info( "File \"" + testOutput.toString() + "\" opened." );
	}

	public final List<Viewable> views() {
		return _views;
	}

	public final void views(final List<Viewable> views) {
		this._views = views;
	}

///Members...
	private static final Logger _log = LoggerFactory.getLogger( TestReporter.class );
	private final int _width  = 1280;
	private final int _height = 768;

	private VBox _center;
	private HBox _container;
	private Stage _primary;
	private List<Viewable> _views;
	private Project _currentProject;
}
