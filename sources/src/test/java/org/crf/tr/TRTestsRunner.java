/**
 * TRTestsRunner.java
 */
package org.crf.tr;


import static org.junit.runners.Suite.SuiteClasses;
import org.crf.tr.tests.layers.FrontendTestLayer;
import org.crf.tr.tests.layers.BackendTestLayer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
@RunWith(Suite.class)
@SuiteClasses({
	FrontendTestLayer.class
   ,BackendTestLayer.class
})
public final class TRTestsRunner {
}
