package de.coinor.rules.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import org.codehaus.groovy.control.CompilerConfiguration;

import de.coinor.rules.CreationCallback;
import de.coinor.rules.Executor;

/**
 * The <code>GroovyExecutor</code> class executes rules written in Groovy.
 * 
 * @author Konstantin Diener, COINOR AG
 */
public class GroovyExecutor implements Executor {
	
	/**
	 * The rule code.
	 */
	private final String code;
	
	/**
	 * The Groovy {@link Binding}.
	 */
	private final Binding binding;

	/**
	 * The compiled Groovy script object.
	 */
	private final Script script;
	
	/**
	 * Creates a <code>GroovyExecutor</code> instance and compiles the rule code
	 * to a {@link Script} object.
	 * 
	 * @param code
	 *            the rule code.
	 */
	public GroovyExecutor(String code) {
		super();
		this.code = code;

		this.binding = new Binding();
		GroovyShell groovyShell = new GroovyShell(binding);
		this.script = groovyShell.parse(this.code);
	}
	
	/**
	 * Creates a <code>GroovyExecutor</code> instance and compiles the rule code
	 * to a {@link Script} object using the given callback.
	 * 
	 * @param code
	 *            the rule code.
	 * @param creationCallback
	 *            the callback to modify the compiler configuration.
	 */
	public GroovyExecutor(String code, CreationCallback creationCallback) {
		super();
		this.code = code;

		this.binding = new Binding();
		CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
		creationCallback.modify(compilerConfiguration);
		GroovyShell groovyShell = new GroovyShell(binding, compilerConfiguration );
		this.script = groovyShell.parse(this.code);
	}

	/* (non-Javadoc)
	 * @see de.coinor.rules.Executor#execute()
	 */
	public Object execute() {
		return this.script.run();		
	}

	/* (non-Javadoc)
	 * @see de.coinor.rules.Executor#getNativeExecutorObject()
	 */
	public Object getNativeExecutorObject() {
		return this.script;
	}
}
