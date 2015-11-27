/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;


import static org.crf.tr.ui.views.Styles.applyOn;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.builders.ChartsBuilder;

import javafx.scene.chart.AreaChart;
import javafx.scene.layout.HBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class TrendsView extends AggregateView<AreaChart<Number, Number>> {

	public TrendsView(final TestReporter owner) {
		super( owner );
	}

//	static final AreaChart makePopulated(final TestsTrendService service, final Date upto) {
//		
//	}

	@Override
    protected final void buildHeaderSection() {
    	final HBox header = new HBox( );
    	applyOn( header, "crf-trends-view-header" );
    	getChildren().add( header );
    }

	@Override
    protected final void buildContentSection() {
    	getChildren().add(chart( ));
    }

	@Override
	protected final AreaChart<Number, Number> makeChart(final TestReporter owner) {
		return ChartsBuilder.buildTrendsMockFor( owner );
	}
}
