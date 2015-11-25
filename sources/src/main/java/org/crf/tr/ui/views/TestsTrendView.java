/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;


import static org.crf.tr.ui.views.Styles.applyCommons;
import static org.crf.tr.ui.views.Styles.applyOn;
import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.builders.TestsTrendsBuilder;

import javafx.scene.chart.AreaChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public class TestsTrendView extends VBox implements Viewable {

	public TestsTrendView(final TestReporter owner) {
		super( );
		this._owner = owner;
		this._trendsChart = TestsTrendsBuilder.makeDefault( owner );
		initWidgets( );
	}

	@Override
	public final void refresh() {
		///ELABORATEME:
	}

	public final TestReporter owner() {
		return _owner;
	}

//	static final AreaChart makePopulated(final TestsTrendService service, final Date upto) {
//		
//	}

	private final void initWidgets( ) {
		applyCommons( this );
		applyOn( this, "crf-trend-view" );
		buildHeaderSection( this, _owner );
		buildContentSection( this, _owner );
	}


    static final void buildHeaderSection(final TestsTrendView view, final TestReporter owner) {
    	final HBox header = new HBox( );
    	applyOn( header, "crf-trends-view-header" );
    	view.getChildren().add( header );
    }

    static final void buildContentSection(final TestsTrendView view, final TestReporter owner) {
    	view.getChildren().add( view._trendsChart );
    }

	private AreaChart<Number, Number> _trendsChart;
	private final TestReporter _owner;
}
