/**
 * EntityExistsException.java
 */
package org.crf.tr.services.signals;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class EntityAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 3476710264115766185L;

	public EntityAlreadyExistsException() {
		super( );
 	}

	public EntityAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityAlreadyExistsException(String message) {
		super(message);
	}

	public EntityAlreadyExistsException(Throwable cause) {
		super(cause);
	}
}
