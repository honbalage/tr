/**
 * AggregateView.java
 */
package org.crf.tr.ui.views;


import static org.crf.tr.ui.views.Styles.applyCommons;
import static org.crf.tr.ui.views.Styles.applyOn;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.builders.Defaults;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public abstract class AggregateView <C extends Chart> extends VBox implements Viewable {

	protected AggregateView(final TestReporter owner) {
		super( );
		this._owner = owner;
		this._aggregateChart = makeChart( _owner );
		initWidgets( );
	}

	private final void initWidgets() {
		applyCommons( this );
		applyOn( this, "crf-aggregate-view" );
		applyOn( this._aggregateChart, "crf-aggregate-view-chart" );
		buildHeaderSection( );
		buildContentSection( );
		buildFooterSection( );
	}

	protected void buildHeaderSection() {
    	final ComboBox<String> datesBox = new ComboBox<>( );
    	final Button refresh = new Button( "Refresh" );
    	//ELABORATEME:
    	Defaults.makeHeaderFor( this )
    	                  .datesBox( datesBox )
    	                  .refresh( refresh )
    	                  .build( );
	}

	protected void buildContentSection() {
    	getChildren().add(chart( ));
	}

	protected void buildFooterSection() {
	}

	@Override
	public void refresh() {
	}

	protected abstract C makeChart(final TestReporter owner);


	public final C chart() {
		return _aggregateChart;
	}

	public final TestReporter owner() {
		return _owner;
	}

	private final TestReporter _owner;
	private final C _aggregateChart;
}
