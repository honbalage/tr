/**
 * DefaultViewBuilder.java
 */
package org.crf.tr.ui.views.builders;



import static org.crf.tr.ui.views.Styles.applyOn;

import org.crf.tr.ui.views.CoverageView;
import org.crf.tr.ui.views.TrendsView;
import org.crf.tr.ui.views.ReportView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.crf.tr.TestReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.Node;

import java.util.List;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Defaults {

	public static final void refreshingHeaderFor(final VBox view, final EventHandler<ActionEvent> onAction) {
		makeHeaderFor( view ).build( );
	}

	public static final <V extends VBox> HeaderBuilder<V> makeHeaderFor(final V view) {
		return new HeaderBuilder<>( view );
	}

	public static final void buildFor(final TestReporter owner, final ReportView reportView) {
		Defaults.<Node>makeFor( owner, reportView )
		                  .addAggregate(new TrendsView( owner ))
		                  .addAggregate(new CoverageView( owner ))
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

	public static final class HeaderBuilder <V extends VBox> {
		private HeaderBuilder(final V view) {
			super( );
			this._view = (( view == null ) ? Optional.empty() : Optional.of( view ));
			this._onAction = Optional.empty( );
			this._datesBox = Optional.empty( );
			this._refresh = Optional.empty( );
		}

		public final HeaderBuilder<V> headerStyle(final String cssClass) {
			this._headerStyle = cssClass;
			return this;
		}

		public final HeaderBuilder<V> datesBoxStyle(final String cssClass) {
			this._comboStyle = cssClass;
			return this;
		}

		public final HeaderBuilder<V> onAction(final EventHandler<ActionEvent> onAction) {
			this._onAction = Optional.of( onAction );
			return this;
		}

		public final HeaderBuilder<V> datesBox(final ComboBox<String> datesBox) {
			this._datesBox = Optional.of( datesBox );
			return this;
		}

		public final HeaderBuilder<V> refresh(final Button refresh) {
			this._refresh = Optional.of( refresh );
			return this;
		}


		public final void build() {
			if (! _view.isPresent( )) {
				_log.error( "Unable to build header for view: view is not set." );
				return;
			}
			final V view = _view.get( );

		   	final HBox header = new HBox( );
	    	applyOn( header, _headerStyle );

	    	final ComboBox<String> datesBox = _datesBox.isPresent() ? _datesBox.get( ) : new ComboBox<>( );
	    	final Button refresh = _refresh.isPresent( ) ? _refresh.get( ) : new Button( "Refresh" );
	    	if (_onAction.isPresent( ))
	    		refresh.setOnAction(_onAction.get( ));

	    	applyOn( datesBox, _comboStyle );
	    	applyOn( refresh );
	    	header.getChildren().add( datesBox );
	    	header.getChildren().add( refresh );
	    	view.getChildren().add( header );
		}


		private static final String _defaultHeaderStyle = "crf-refreshing-header";
		private static final String _defaultComboStyle = "crf-dates-box";
		private String _headerStyle = _defaultHeaderStyle;
		private String _comboStyle = _defaultComboStyle;

		private Optional<EventHandler<ActionEvent>> _onAction;
		private Optional<ComboBox<String>> _datesBox;
		private Optional<Button> _refresh;
		private final Optional<V> _view;
	}

	private static final Logger _log = LoggerFactory.getLogger( Defaults.class );
}
