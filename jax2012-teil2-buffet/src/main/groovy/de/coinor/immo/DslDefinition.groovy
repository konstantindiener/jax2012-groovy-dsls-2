package de.coinor.immo

abstract class DslDefinition extends Script {
	
	public DslDefinition() {
		Boolean.metaClass.Und { Boolean b ->
			delegate & b
		}
	}

	def Wenn(Map m, boolean condition) {
		if (condition) {
			m.Dann
		}
		else {
			m.Sonst
		}
	}
	
	def Verzweige(Map m, def key) {
		return m[key];
	}
}
