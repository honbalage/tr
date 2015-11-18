/**
 * Processor.java
 */
package org.crf.tr.commands;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public interface Processor <P extends Process> {

	public void process(final P process);
}
