package de.coinor.rules;

/**
 * The <code>Rule</code> capsulates a generic rule object.
 * 
 * @author Konstantin Diener, COINOR AG
 */
public class Rule {

	/**
	 * The rule implementation language.
	 */
	private Language language;

	/**
	 * The rule code.
	 */
	private String code;

	/**
	 * The cached native rule executor.
	 */
	Executor executor;

	/**
	 * Creates a new <code>Rule</code>.
	 * 
	 * @param language
	 *            the rule implementation language.
	 * @param code
	 *            the rule code.
	 */
	public Rule(Language language, String code) {
		super();
		this.language = language;
		this.code = code;
	}

	/**
	 * Creates and returns the native rule executor.
	 * 
	 * @return the native executor.
	 */
	public Executor executor() {
		return executor(null);
	}

	/**
	 * Creates and returns the native rule executor.
	 * 
	 * @param creationCallback
	 *            the callback to invoke during creation.
	 * @return the native executor.
	 */
	public Executor executor(CreationCallback creationCallback) {
		if (executor == null) {
			executor = this.language.executor(this.code, creationCallback);
		}

		return executor;
	}
}
