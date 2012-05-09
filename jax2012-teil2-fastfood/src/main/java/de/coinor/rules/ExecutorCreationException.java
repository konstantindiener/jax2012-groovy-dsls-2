package de.coinor.rules;

/**
 * An <code>ExecutorCreationException</code> is thrown if the creation of a
 * native rule object fails.
 * 
 * @author Konstantin Diener, COINOR AG
 */
@SuppressWarnings("serial")
public class ExecutorCreationException extends RuntimeException {

	/**
	 * Creates a new <code>ExecutorCreationException</code>.
	 */
	public ExecutorCreationException() {
		super();
	}

	/**
	 * Creates a new <code>ExecutorCreationException</code>.
	 * 
	 * @param message
	 *            the exception message.
	 * @param cause
	 *            the exception cause.
	 */
	public ExecutorCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new <code>ExecutorCreationException</code>.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ExecutorCreationException(String message) {
		super(message);
	}

	/**
	 * Creates a new <code>ExecutorCreationException</code>.
	 * 
	 * @param cause
	 *            the exception cause.
	 */
	public ExecutorCreationException(Throwable cause) {
		super(cause);
	}
}
