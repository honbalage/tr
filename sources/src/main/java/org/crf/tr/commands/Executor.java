/**
 * Executor.java
 */
package org.crf.tr.commands;

import java.util.List;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Executor {

	public static final int inShell(final List<String> command, final Processor<? super Process> function) {
		final ProcessBuilder pb = new ProcessBuilder( command );
		pb.redirectErrorStream( true );
		try {
			
			final Process p = pb.start( );
			function.process( p );
			return p.exitValue();
		} catch( final Exception e ) {
			// TODONE: throw..
		}
		return -1;
	}
}
