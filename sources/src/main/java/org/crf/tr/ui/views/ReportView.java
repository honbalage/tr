/**
 * ReportVIew.java
 */
package org.crf.tr.ui.views;

import static org.crf.tr.ui.views.Styles.applyOnReportView;
import static org.crf.tr.ui.views.Styles.applyOn;

import java.nio.file.Path;
import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.builders.Defaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
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
		this.getChildren().add(asScrollable(applyOn(buildContentSection( this, _owner ), "crf-report-view-contents", "crf-report-view-contents-commons" )));
		buildCommentSection( this, _owner );
		buildFooterSection( this, _owner );
	}

	static final ScrollPane asScrollable(final Pane content) {
		final ScrollPane wrapper = new ScrollPane( content );
		applyOn( wrapper, "crf-report-view-contents-commons" );
		return wrapper;
	}

	static final void buildHeaderSection(final ReportView view, final TestReporter owner) {
		final ComboBox<String> datesBox = new ComboBox<>( );
		final Button refresh = new Button( "Refresh" );
		//ELABORATEME:
		Defaults.makeHeaderFor( view )
		                  .datesBox( datesBox )
		                  .refresh( refresh )
		                  .build();
	}


	protected abstract Pane buildContentSection(final ReportView view, final TestReporter owner);
	public abstract void export(final Path outputPdf);

	static final void buildCommentSection(final ReportView view, final TestReporter owner) {
		final TitledPane comments = new TitledPane( "Comments", view.commentArea( ));
		applyOn( view.commentArea(), "crf-report-comments-area" );
		applyOn( comments, "crf-report-view-comments-wrapper" );
		view.getChildren().add( comments );
	}

	static final void buildFooterSection(final ReportView view, final TestReporter owner) {
		final HBox footer = new HBox( );
		final Button export = Styles.applyOn(new Button( "Export" ));
		export.setOnAction( evt -> {
			///ELABORATEME: DialogFactory.makeExportFor( view, owner );
			_log.info( "Report exported.." );
		});
		export.setTooltip(new Tooltip( "Press to export to a PDF file." ));
		footer.getChildren().add( export );
		applyOn( export, "crf-report-view-export-btn" );
		applyOn( footer, "crf-refreshing-header" );
		view.getChildren().add( footer );
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
