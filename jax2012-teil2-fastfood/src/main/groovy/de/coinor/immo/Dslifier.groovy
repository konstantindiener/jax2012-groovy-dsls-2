package de.coinor.immo

import java.util.Map;

import de.coinor.rules.CreationCallback
import de.coinor.rules.Executor
import de.coinor.rules.Rule;

import groovy.lang.Script;

class Dslifier {

	private final Script s;

	public Dslifier(def s) {
		this.s = s;
	}

	public void dslify(Map<String, Object> properties, Map<String, Rule> formulas, CreationCallback creationCallback) {
		s.metaClass.getProperty { String name ->
			Rule formula = formulas[name]
			if (formula != null) {
				Executor executor = formula.executor(creationCallback)
				new Dslifier(executor.getNativeExecutorObject()).dslify(properties, formulas, creationCallback)
				
				executor.execute()
			}
			else {
				def propertyValue = properties[name]
				if (propertyValue != null) {
					propertyValue
				}
				else {
					delegate.@"$name"
				}
			}
		}
	}
}
