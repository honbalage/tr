/**
 * OpenProjectBuilder.java
 */
package org.crf.tr.ui.factories.builders;

import java.util.List;
import org.crf.tr.TestReporter;
import org.crf.tr.model.Project;
import org.crf.tr.services.factories.Services;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ManageProjectBuilder {

	public static final Dialog<Project> buildFor(final TestReporter owner) {
		final Dialog<Project> dialog = new Dialog<>( );
		dialog.setTitle( "Manage Projects" );
		
		final List<Project> projects = Services.makeForProjects().listAll( );
		final ListView<Project> projectsView = new ListView<>(FXCollections.observableArrayList( projects ));
		projectsView.setEditable( false );
		final Pane content = makeOpenProjectPane( owner, projectsView );
		
		final ButtonType open = new ButtonType( "Open", ButtonData.OK_DONE );
		final ButtonType remove = new ButtonType( "Remove", ButtonData.OK_DONE );
		final ButtonType cancel = new ButtonType( "Cancel", ButtonData.CANCEL_CLOSE );
		dialog.getDialogPane().getButtonTypes().add( open );
		dialog.getDialogPane().getButtonTypes().add( remove );
		dialog.getDialogPane().getButtonTypes().add( cancel );
		dialog.setResultConverter( button -> {
			if (cancel.equals( button ) || remove.equals( button )) return null;
			return projectsView.getSelectionModel().getSelectedItem();
		});

		final Node removeBtn = dialog.getDialogPane().lookupButton( remove );
		removeBtn.addEventFilter( EventType.ROOT , evt -> {
			if (ActionEvent.ACTION.equals(evt.getEventType( ))) {
				final Project selected = projectsView.getSelectionModel().getSelectedItem();
				if (selected != null) {
					Services.makeForProjects().remove(selected.key( ));
					projectsView.getItems().remove( selected );
				}
				evt.consume( );
			}
		});
		dialog.getDialogPane().setContent( content );
		return dialog;
	}

	static final Pane makeOpenProjectPane(final TestReporter owner, final Node... nodes) {
		final StackPane stack = new StackPane( );
		stack.getChildren().add( nodes[0] );
		return stack;
	}
}
