/**
 * DialogFactory.java
 */
package org.crf.tr.ui.factories;


import javafx.scene.control.Dialog;
import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.ui.factories.builders.NewProjectBuilder;
import org.crf.tr.ui.factories.builders.OpenProjectBuilder;
import org.crf.tr.ui.factories.builders.ShellBuilder;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
///REFINEME: create separate builder classes for each public makeXxx() function ;)
public final class DialogFactory {

	public static final Dialog<Project> makeOpenProjectFor(final TestReporter owner) {
		return OpenProjectBuilder.buildFor( owner );
	}

	public static final Dialog<Project> makeNewProjectFor(final TestReporter owner) {
		return NewProjectBuilder.buildFor( owner );
	}

	public static final Dialog<String> makeShellFor(final TestReporter owner) {
		return ShellBuilder.buildFor( owner );
	}
}
