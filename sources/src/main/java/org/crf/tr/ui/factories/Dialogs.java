/**
 * DialogFactory.java
 */
package org.crf.tr.ui.factories;


import java.io.File;

import javafx.scene.control.Dialog;
import java.util.Optional;
import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.ui.factories.builders.ImportTestOutputBuilder;
import org.crf.tr.ui.factories.builders.NewProjectBuilder;
import org.crf.tr.ui.factories.builders.ManageProjectBuilder;
import org.crf.tr.ui.factories.builders.ShellBuilder;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Dialogs {

	public static final Dialog<Project> makeManageProjectFor(final TestReporter owner) {
		return ManageProjectBuilder.buildFor( owner );
	}

	public static final Dialog<Project> makeNewProjectFor(final TestReporter owner) {
		return NewProjectBuilder.buildFor( owner );
	}

	public static final Dialog<String> makeShellFor(final TestReporter owner) {
		return ShellBuilder.buildFor( owner );
	}

	public static final Optional<Dialog<File>> makeImportTestOutputFor(final TestReporter owner) {
		return ImportTestOutputBuilder.buildFor( owner );
	}
}
