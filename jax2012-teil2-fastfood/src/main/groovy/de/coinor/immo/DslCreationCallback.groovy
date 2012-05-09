package de.coinor.immo

import de.coinor.rules.CreationCallback
import org.codehaus.groovy.control.CompilerConfiguration

public class DslCreationCallback implements CreationCallback {

	private final String dslBaseClassName;

	public DslCreationCallback(String dslBaseClassName) {
		this.dslBaseClassName = dslBaseClassName;
	}

	@Override
	public void modify(Object nativeFactoryConfiguration) {
		CompilerConfiguration cc = nativeFactoryConfiguration;
		
		cc.setScriptBaseClass(dslBaseClassName);
	}
}
