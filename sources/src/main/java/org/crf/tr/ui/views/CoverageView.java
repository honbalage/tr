/**
 * CoverageView.java
 */
package org.crf.tr.ui.views;


import org.crf.tr.ui.views.builders.ChartsBuilder;
import javafx.scene.chart.LineChart;
import org.crf.tr.TestReporter;

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
	protected LineChart<String, Number> makeChart(TestReporter owner) {
		return ChartsBuilder.buildCoverageMockFor( owner );
	}
}
