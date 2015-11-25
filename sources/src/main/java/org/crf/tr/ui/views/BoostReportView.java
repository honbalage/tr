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
	protected final Pane buildContentSection(ReportView view, TestReporter owner) {
		final GridPane content = new GridPane( );
		///ELABORATEME: setup content of the grid..
		return content;
	}

	@Override
	public final void export(final Path outputPdf) {
		
	}

//	private final BoostReportService _service;
}
