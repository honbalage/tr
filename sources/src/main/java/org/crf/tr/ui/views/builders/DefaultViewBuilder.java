/**
 * DefaultViewBuilder.java
 */
package org.crf.tr.ui.views.builders;



import static org.crf.tr.ui.views.Styles.applyOn;
import org.crf.tr.ui.views.TestsTrendView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.crf.tr.TestReporter;
import javafx.scene.Node;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class DefaultViewBuilder {

	public static final void buildFor(final TestReporter owner, final Node reportView) {
		final HBox container = owner.container();
		container.setSpacing( 7 );
		final List<Node> views = Arrays.asList( reportView, new TestsTrendView( owner ));
		final List<Node> children = container.getChildren();
		children.clear();
		children.add(views.get( 0 ));

		final VBox chartsHolder = new VBox();
		applyOn( chartsHolder, "crf-charts-view-holder" );
		chartsHolder.getChildren().add(views.get( 1 ));
		children.add( chartsHolder );
	}
}
