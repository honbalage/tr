/**
 * BoostReportView.java
 */
package org.crf.tr.ui.views;

import java.nio.file.Path;

import org.crf.tr.TestReporter;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class BoostReportView extends ReportView {

	public BoostReportView(final TestReporter owner) {
		super( owner );
//		_service = Services.makeForBoostReports( );
	}

	@Override
	protected final Pane buildContentSection(final ReportView view,final TestReporter owner) {
		final GridPane content = new GridPane( );
		///ELABORATEME: setup content of the grid..
		return content;
	}

	@Override
	public final void refresh() {
		//ELABORATEME:
		//final Optional<BoostReport> report = _service.find( filepath );
		//if (! report.isPresent()) return; // TODONE: handle errors :)
	}

	@Override
	public final void export(final Path outputPdf) {
		
	}

//	private final BoostReportService _service;
}
