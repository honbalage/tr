/**
 * Project.java
 */
package org.crf.tr.model;

import static java.lang.String.format;

import org.crf.tr.ui.views.builders.BoostViewBuilder;
import org.crf.tr.ui.views.builders.CloverViewBuilder;
import org.crf.tr.ui.views.builders.ViewBuilder;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Project {

	public Project(final long id, final String name, final TestFramework framework) {
		super( );
		_id = id;
		_name = name;
		_framework = framework;
	}
	
	public Project(final String name, final TestFramework framework) {
		this( -1, name, framework );
	}

	///RFU: for DataSources ;)
	///due to Database id-creation policy..
	public final long id() {
		return _id;
	}
	
	public final void id(final long id) {
		this._id = id;
	}

	public final String name() {
		return _name;
	}
	
	public final TestFramework framework() {
		return _framework;
	}

	public final Key key() {
		return new Key( this );
	}

	@Override
	public final String toString() {
		return format("%s [%s]", _name, _framework.toString( ));
	}

	/**
	 * Specifies an ID through which the database, data-persistence layer
	 * will identify this project.
	 * */
	private long _id;
	
	/**
	 * Name and test framework would identify the project on a GUI and 
	 * user level. The (name, framework) pair must be unique!!!
	 * */
	private final String _name;
	private final TestFramework _framework;

	public static enum TestFramework {
		Clover(new CloverViewBuilder( ))
	   ,Boost(new BoostViewBuilder( ));
		
		TestFramework(final ViewBuilder builder) {
			this._viewBuilder = builder;
		}
		
		public final ViewBuilder viewBuilder() {
			return _viewBuilder;
		}
		
		private final ViewBuilder _viewBuilder;
	}


	/**
	 * Encapsulates equality check logic for a <code>Project</code> instance.
	 * 
	 * */
	public static final class Key {
		
		Key(final Project owner) {
			this._owner = owner;
		}
		
		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public final int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((_owner.framework() == null) ? 0 : _owner.framework().hashCode());
			result = prime * result
					+ ((_owner.name() == null) ? 0 : _owner.name().hashCode());
			return result;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public final boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Key)) {
				return false;
			}
			final Key other = (Key) obj;
			if (_owner.framework() != other._owner.framework()) {
				return false;
			}
			if (_owner.name() == null) {
				if (other._owner.name() != null) {
					return false;
				}
			} else if (!_owner.name().equals(other._owner.name())) {
				return false;
			}
			return true;
		}

		private final Project _owner;
	}
}
