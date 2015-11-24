/**
 * BoostReportView.java
 */
package org.crf.tr.ui.views;

import org.crf.tr.TestReporter;
import org.crf.tr.model.BoostReport;
import org.crf.tr.services.BoostReportService;
import org.crf.tr.services.factories.Services;

import javafx.scene.layout.VBox;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class BoostReportView extends VBox implements Viewable {

	public BoostReportView(final TestReporter owner) {
		super( );
		_owner = owner;
//		_service = Services.makeForBoostReports( );
		Styles.applyForReportView( this );

	}

	@Override
	public void refresh() {
		// TODONE Auto-generated method stub
	}

//	private BoostReport _report;
	private final TestReporter _owner;
//	private final BoostReportService _service;
}
