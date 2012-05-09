package de.coinor.rules;

/**
 * The <code>Executor</code> interface defines the functionality of rule
 * language implementations.
 * 
 * @author Konstantin Diener, COINOR AG
 */
public interface Executor {

	/**
	 * Executes the rule.
	 * 
	 * @return the rule result.
	 */
	Object execute();

	/**
	 * Returns the native implementation of the rule.
	 * 
	 * @return the object of the rule's native implementation.
	 */
	Object getNativeExecutorObject();
}
