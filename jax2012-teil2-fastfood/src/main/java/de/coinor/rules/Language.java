package de.coinor.rules;

import java.lang.reflect.InvocationTargetException;

/**
 * The <code>Language</code> class defines a {@link Rule} implementation
 * language.
 * 
 * @author Konstantin Diener, COINOR AG
 */
public class Language {

	/**
	 * The language name.
	 */
	private String name;

	/**
	 * The class that implements the creation and execution of the native rule
	 * implementation.
	 */
	private Class<? extends Executor> executorClass;

	/**
	 * Creates a new <code>Language</code>.
	 * 
	 * @param name
	 *            the language name.
	 * @param executorClass
	 *            the class that implements the creation and execution of the
	 *            native rule implementation.
	 */
	public Language(String name, Class<? extends Executor> executorClass) {
		super();
		this.name = name;
		this.executorClass = executorClass;
	}

	/**
	 * Creates an executor for the given rule code.
	 * 
	 * @param code
	 *            the rule code.
	 * @return the executor.
	 */
	public Executor executor(String code) {
		return executor(code, null);
	}

	/**
	 * Creates an executor for the given rule code.
	 * 
	 * @param code
	 *            the rule code.
	 * @param creationCallback
	 *            the callback to invoke during creation of the native rule
	 *            implementation.
	 * @return the executor.
	 */
	public Executor executor(String code, CreationCallback creationCallback) {
		try {
			if (creationCallback == null) {
				return this.executorClass.getConstructor(
						new Class[] { String.class }).newInstance(code);
			} else {
				return this.executorClass.getConstructor(
						new Class[] { String.class, CreationCallback.class })
						.newInstance(code, creationCallback);
			}
		} catch (IllegalArgumentException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		} catch (SecurityException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		} catch (InstantiationException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		} catch (IllegalAccessException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		} catch (InvocationTargetException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		} catch (NoSuchMethodException e) {
			throw new ExecutorCreationException(
					"Cannot create executor object.", e);
		}
	}

	@Override
	public String toString() {
		return "Language [name=" + name + "]";
	}
}
