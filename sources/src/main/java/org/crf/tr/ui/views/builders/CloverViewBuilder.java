/**
 * CloverViewBuilder.java
 */
package org.crf.tr.ui.views.builders;

import static org.crf.tr.ui.views.Styles.applyOn;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.CloverReportView;
import org.crf.tr.ui.views.TestsTrendView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class CloverViewBuilder implements ViewBuilder {

	@Override
	public void buildViewsFor(final TestReporter owner) {
		_log.info( "Building view for clover.." ); //< TODONE: remove

		final HBox container = owner.container();
		container.setSpacing( 7 );
		final List<Node> views = Arrays.asList(new CloverReportView( owner ), new TestsTrendView( owner ));
		final List<Node> children = container.getChildren();
		children.clear();
		children.add(views.get( 0 ));

		final VBox chartsHolder = new VBox();
		applyOn( chartsHolder, "crf-charts-view-holder" );
		chartsHolder.getChildren().add(views.get( 1 ));
		children.add( chartsHolder );
	}

	private static final Logger _log = LoggerFactory.getLogger( BoostViewBuilder.class );
}
