/**
 * ServiceFactory.java
 */
package org.crf.tr.services.factories;

import org.crf.tr.services.BoostReportService;
import org.crf.tr.services.CloverReportService;
import org.crf.tr.services.ProjectService;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Services {

	public static final ProjectService makeForProjects() {
		return new ProjectService();
	}
	
	public static final BoostReportService makeForBoostReports() {
		return new BoostReportService();
	}
	
	public static final CloverReportService makeForCloverReports() {
		return new CloverReportService();
	}
}
