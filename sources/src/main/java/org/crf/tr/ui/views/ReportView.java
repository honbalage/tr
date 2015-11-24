/**
 * ReportVIew.java
 */
package org.crf.tr.ui.views;

import static org.crf.tr.ui.views.ProjectHeader.makeStyledLabel;

import java.nio.file.Path;

import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
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
		Styles.applyForReportView( this );
		buildHeaderSection(  this, _owner );
		/// content section is in a scroll bar in case it would not fit ;)
		this.getChildren().add(asScrollable(buildContentSection( this, _owner )));
		buildCommentSection( this, _owner );
	}

	static final ScrollPane asScrollable(final Pane content) {
		final ScrollPane wrapper = new ScrollPane( content );
		content.setMinHeight( 330 );
		wrapper.setMinHeight(content.getMinHeight( ));
		return wrapper;
	}

	static final void buildHeaderSection(final ReportView view, final TestReporter owner) {
		final GridPane header = new GridPane( );
		header.setHgap( 3 );
		header.setVgap( 3 );
		header.setPadding(new Insets( 4, 4, 7, 4 ));
		final Project current = owner.currentProject();
		
		final String labelStyle = "-fx-text-fill: #ffffff;";
		final String textStyle = labelStyle + " -fx-font-weight: bold; -fx-font-size: 13";
		header.add( makeStyledLabel( "Project: ", labelStyle ) , 1, 1 );
		header.add( makeStyledLabel(current.name( ), textStyle), 2, 1 );
		header.add( makeStyledLabel( "Test FW: ", labelStyle ), 1, 2 );
		header.add( makeStyledLabel(current.framework().toString( ), textStyle), 2, 2 );

		header.setStyle( "-fx-background-color: deepskyblue;" );
		view.getChildren().add( header );
	}


	protected abstract Pane buildContentSection(final ReportView view, final TestReporter owner);
	public abstract void print(final Path outputPdf);

	static final void buildCommentSection(final ReportView view, final TestReporter owner) {
		final TextArea commentArea = new TextArea( );
		final TitledPane comments = new TitledPane( "Comments", commentArea );
		comments.setMinHeight( 100 );
		comments.setCollapsible( false );

		view.getChildren().add( comments );
	}

	
	@Override
	public final void refresh() {
		///ELABORATEME:
	}
	
	protected final TestReporter _owner;
	protected final TextArea _commentArea;
}
