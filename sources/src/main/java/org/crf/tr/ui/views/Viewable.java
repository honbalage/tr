/**
 * Viewable.java
 */
package org.crf.tr.ui.views;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public interface Viewable {

	/**
	 * Triggers a refresh on the <code>Viewable</code> instance that updates the
	 * data presented by it.
	 * */
	public default void refresh() {
	}

	/**
	 * Triggers a refresh (lookup) on the <code>Viewable</code> instance that updates the
	 * data presented by it according to the <code>date</code> passed (represented as a <code>String</code>).
	 * 
	 * @param date
	 * */
	public default void refresh(final String date) {
	}
}
