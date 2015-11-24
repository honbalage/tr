/**
 * Strings.java
 */
package org.crf.tr.tools;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Strings {

	public static final boolean isEmpty(final String string) {
		return (( string == null ) || ( string.isEmpty() ));
	}

	private Strings() {
		super( );
	}
}
