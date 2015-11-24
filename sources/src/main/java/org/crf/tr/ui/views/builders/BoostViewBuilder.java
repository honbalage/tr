/**
 * BoostViewBuilder.java
 */
package org.crf.tr.ui.views.builders;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.BoostReportView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class BoostViewBuilder implements ViewBuilder {

	@Override
	public void buildViewsFor(final TestReporter owner) {
		_log.info( "Building view for boost.." ); //< TODONE: remove

		final HBox container = owner.container();

		final List<Node> views = Arrays.asList( new BoostReportView(owner) );
		final List<Node> children = container.getChildren();
		children.clear();
		children.add(views.get( 0 ));
        
//        final HBox chartsHolder = new HBox();
//        children.add( chartsHolder );
//        //for i in [1, 2): chartsHolder.add(views[ i ])
//
//		final List<Node> items = center.getChildren();
//		if (items.size() < 2) items.add( 1, container );
//		else items.set( 1 , container );
	}

	private static final Logger _log = LoggerFactory.getLogger( BoostViewBuilder.class );
}
