/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;


import org.crf.tr.ui.views.builders.ChartsBuilder;
import javafx.scene.chart.AreaChart;
import org.crf.tr.TestReporter;


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
	protected final AreaChart<Number, Number> makeChart(final TestReporter owner) {
		return ChartsBuilder.buildTrendsMockFor( owner );
	}
}
