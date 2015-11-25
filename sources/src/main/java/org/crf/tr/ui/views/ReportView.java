/**
 * ReportVIew.java
 */
package org.crf.tr.ui.views;

import static org.crf.tr.ui.views.Styles.applyOnReportView;
import static org.crf.tr.ui.views.Styles.applyOn;
import java.nio.file.Path;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public abstract class ReportView extends VBox implements Viewable {

	public ReportView(final TestReporter owner) {
		super( );
		this._owner = owner;
		this._commentArea = new TextArea( );
		initWidgets( );
	}

	private final void initWidgets( ) {
		applyOnReportView( this );
		buildHeaderSection(  this, _owner );
		/// content section is in a scroll bar in case it would not fit ;)
		this.getChildren().add(asScrollable(buildContentSection( this, _owner )));
		buildCommentSection( this, _owner );
		buildFooterSection( this, _owner );
	}

	static final ScrollPane asScrollable(final Pane content) {
		final ScrollPane wrapper = new ScrollPane( content );
		content.setMinHeight( 460 );
		wrapper.setMinHeight(content.getMinHeight( ));
		return wrapper;
	}

	static final void buildHeaderSection(final ReportView view, final TestReporter owner) {
		final GridPane header = new GridPane( );
		header.setHgap( 3 );
		header.setVgap( 3 );
		header.setPadding(new Insets( 4, 4, 7, 4 ));
		final Project current = owner.currentProject();

		header.add( applyOn(new Label( "Project: " ), "crf-report-view-header-label" ) , 1, 1 );
		header.add( applyOn(new Label(current.name( )), "crf-report-view-header-text" ), 2, 1 );
		header.add( applyOn(new Label( "Test FW: " ), "crf-report-view-header-label" ), 1, 2 );
		header.add( applyOn(new Label(current.framework().toString( )), "crf-report-view-header-text" ), 2, 2 );

		applyOn( header, "crf-report-view-header" );
		view.getChildren().add( header );
	}


	protected abstract Pane buildContentSection(final ReportView view, final TestReporter owner);
	public abstract void export(final Path outputPdf);

	static final void buildCommentSection(final ReportView view, final TestReporter owner) {
		final TitledPane comments = new TitledPane( "Comments", view.commentArea( ));
		comments.setMinHeight( 70 );
		comments.setCollapsible( false );
		applyOn( comments, "crf-report-view-comments-wrapper" );

		view.getChildren().add( comments );
	}

	static final void buildFooterSection(final ReportView view, final TestReporter owner) {
		final HBox footer = new HBox( );
		footer.setPadding(new Insets( 7, 13, 7, 7 ));
		footer.setNodeOrientation( NodeOrientation.RIGHT_TO_LEFT );
		final Button export = Styles.applyOn(new Button( "Export" ));
		export.setOnAction( evt -> {
			///ELABORATEME: DialogFactory.makeExportFor( view, owner );
			_log.info( "Report exported.." );
		});
		export.setMinWidth( 130 );
		export.setTooltip(new Tooltip( "Press to export to a PDF file." ));
		footer.getChildren().add( export );
		applyOn( footer, "crf-report-view-footer" );
		view.getChildren().add( footer );
	}
	
	@Override
	public final void refresh() {
		///ELABORATEME:
		_log.info( "View refreshed.." );
	}

	public final TestReporter owner() {
		return _owner;
	}

	protected final TextArea commentArea() {
		return _commentArea;
	}

	private static final Logger _log = LoggerFactory.getLogger( ReportView.class );
	private final TestReporter _owner;
	private final TextArea _commentArea;
}
