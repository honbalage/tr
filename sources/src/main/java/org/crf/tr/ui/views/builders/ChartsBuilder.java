/**
 * TestsTrends.java
 */
package org.crf.tr.ui.views.builders;


import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.crf.tr.TestReporter;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ChartsBuilder {

	@SuppressWarnings("unchecked")
	public static final AreaChart<Number, Number> buildTrendsMockFor(final TestReporter owner) {
		final NumberAxis percentages = new NumberAxis( 0, 100, 10 );
		final NumberAxis points = new NumberAxis( 0, 100, 5 );

		final AreaChart<Number, Number> trendsChart = new AreaChart<>( points, percentages );
		trendsChart.setTitle( "Tests Trend: Defaults" );

		final XYChart.Series<Number, Number> successTrend = new XYChart.Series<>( );
		final XYChart.Series<Number, Number> failTrend = new XYChart.Series<>( );
		successTrend.setName( "Success [%]" );
		failTrend.setName( "Fail [%]" );
		for( int i = 0; i < 100; ++i ) {
			successTrend.getData().add(new XYChart.Data<Number, Number>( 2*i, 2*i ));
			failTrend.getData().add(new XYChart.Data<Number, Number>( 2*i, 100-2*i ));
		}
		trendsChart.getData().addAll( successTrend, failTrend );
		return trendsChart;
	}

	public static final LineChart<String, Number> buildCoverageMockFor(final TestReporter owner) {
		final NumberAxis percentages = new NumberAxis( 0, 100, 10 );
		final CategoryAxis dates = new CategoryAxis();
		
		final LineChart<String, Number> coverageChart = new LineChart<>( dates, percentages );
		coverageChart.setTitle( "Test Coverage" );
		final String format = "2015.01.%s";
		final XYChart.Series<String, Number> series = new XYChart.Series<>( );
		series.setName( "Coverage [%]" );
		for( int i = 10; i < 100; i+=10 ) {
			series.getData().add(new XYChart.Data<String, Number>(String.format( format, i ), i ));
		}
		coverageChart.getData().add( series );
		return coverageChart;
	}
}
