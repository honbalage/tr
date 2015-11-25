/**
 * TestsTrends.java
 */
package org.crf.tr.ui.views.builders;

import static org.crf.tr.ui.views.Styles.applyOn;
import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.Styles;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class TestsTrendsBuilder {

	@SuppressWarnings("unchecked")
	public static final AreaChart<Number, Number> makeDefault(final TestReporter owner) {
		final NumberAxis percentages = new NumberAxis( 0, 100, 2 );
		final NumberAxis points = new NumberAxis( 0, 100, 1 );

		final AreaChart<Number, Number> trendsChart = new AreaChart<>( points, percentages );
		trendsChart.setTitle( "Tests Trend: Defaults" );

		final XYChart.Series<Number, Number> successTrend = new XYChart.Series<>( );
		final XYChart.Series<Number, Number> failTrend = new XYChart.Series<>( );
		successTrend.setName( "Success" );
		successTrend.setName( "Fail" );
		for( int i = 0; i < 100; ++i ) {
			successTrend.getData().add(new XYChart.Data<Number, Number>( 2*i, i ));
			failTrend.getData().add(new XYChart.Data<Number, Number>( 100-2*i, 100-i ));
		}
		trendsChart.getData().addAll( successTrend, failTrend );
		applyOn( trendsChart, "crf-trends-view-chart" );
		return trendsChart;
	}
}
