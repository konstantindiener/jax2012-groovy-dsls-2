package de.coinor.rules;

/**
 * The <code>CreationCallback</code> allows the client of a {@link Rule} to
 * modify the underlying native rule object factory of its {@link Executor}.
 * 
 * @author Konstantin Diener, COINOR AG
 */
public interface CreationCallback {

	/**
	 * The callback method is called during creation of a native rule
	 * implementation and allows to modify the given object.
	 * 
	 * @param nativeFactoryConfiguration
	 *            the native factory configuration to modify.
	 */
	void modify(Object nativeFactoryConfiguration);
}
