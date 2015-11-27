/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;

import static org.crf.tr.ui.views.Styles.applyOn;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.builders.ChartsBuilder;

import javafx.scene.chart.LineChart;
import javafx.scene.layout.HBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class CoverageView extends AggregateView<LineChart<String, Number>>{

	public CoverageView(final TestReporter owner) {
		super(owner);
	}

	@Override
	protected void buildHeaderSection() {
    	final HBox header = new HBox( );
    	applyOn( header, "crf-trends-view-header" );
    	getChildren().add( header );
	}

	@Override
	protected void buildContentSection() {
    	getChildren().add(chart( ));	
	}

	@Override
	protected LineChart<String, Number> makeChart(TestReporter owner) {
		return ChartsBuilder.buildCoverageMockFor( owner );
	}
}
