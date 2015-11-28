/**
 * CloverReportView.java
 */
package org.crf.tr.ui.views;

import java.nio.file.Path;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.crf.tr.TestReporter;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class CloverReportView extends ReportView {

	public CloverReportView(final TestReporter owner) {
		super( owner );
//		_service = Services.makeForCloverReports( );
	}

	@Override
	protected final Pane buildContentSection(final ReportView view, final TestReporter owner) {
		final GridPane content = new GridPane( );
		///ELABORATEME: setup content of the grid..
		return content;
	}

	@Override
	public final void refresh() {
		//ELABORATEME:
		//final Optional<CloverReport> report = _service.find( filepath );
		//if (! report.isPresent()) return; // TODONE: handle errors :)
	}

	@Override
	public final void export(final Path outputPdf) {
		
	}

//	private final CloverReportService _service;
}
