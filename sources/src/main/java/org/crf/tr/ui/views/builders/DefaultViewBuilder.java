/**
 * DefaultViewBuilder.java
 */
package org.crf.tr.ui.views.builders;



import static org.crf.tr.ui.views.Styles.applyOn;
import org.crf.tr.ui.views.TestsTrendView;
import org.crf.tr.ui.views.ReportView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.crf.tr.TestReporter;
import java.util.ArrayList;
import javafx.scene.Node;
import java.util.List;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class DefaultViewBuilder {
	public static final void buildFor(final TestReporter owner, final ReportView reportView) {
		DefaultViewBuilder.<Node>makeFor( owner, reportView )
		                  .addAggregate(new TestsTrendView( owner ))
		                  .build( );
	}

	public static final <A extends Node> Builder<A> makeFor(final TestReporter owner, final ReportView reportView) {
		return new Builder<A>( owner, reportView );
	}


	public static final class Builder <A extends Node> {
		private Builder(final TestReporter owner, final ReportView reportView) {
			super( );
			this._owner = owner;
			this._reportView = reportView;
			this._aggregateViews = new ArrayList<>( );
		}
	
		public final Builder<A> addAggregate(final A aggregateView) {
			this._aggregateViews.add( aggregateView );
			return this;
		}
	
		public final void build( ) {
			final HBox container = _owner.container();
			container.setSpacing( 7 );
			final List<Node> children = container.getChildren();
			children.clear();
			children.add( _reportView );
	
			final VBox aggregatesHolder = new VBox();
			applyOn( aggregatesHolder, "crf-aggregate-views-holder" );
	        aggregatesHolder.getChildren().addAll( _aggregateViews );
			children.add( aggregatesHolder );
		}

		private final TestReporter _owner;
		private final ReportView _reportView;
		private final List<A> _aggregateViews;
	}
}
