/**
 * BoostViewBuilder.java
 */
package org.crf.tr.ui.views.builders;


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
		DefaultViewBuilder.buildFor( owner, new BoostReportView( owner ));
	}

	private static final Logger _log = LoggerFactory.getLogger( BoostViewBuilder.class );
}
