/**
 * CloverViewBuilder.java
 */
package org.crf.tr.ui.views.builders;

import org.crf.tr.TestReporter;
import org.crf.tr.ui.views.CloverReportView;
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
		DefaultViewBuilder.buildFor( owner, new CloverReportView( owner ));
	}

	private static final Logger _log = LoggerFactory.getLogger( BoostViewBuilder.class );
}
