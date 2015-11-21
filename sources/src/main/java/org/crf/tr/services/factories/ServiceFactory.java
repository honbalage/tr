/**
 * ServiceFactory.java
 */
package org.crf.tr.services.factories;

import org.crf.tr.services.ProjectService;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ServiceFactory {

	public static final ProjectService makeForProjects() {
		return new ProjectService();
	}
}
