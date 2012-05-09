package de.coinor.immo;

import static org.junit.Assert.assertEquals;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;

import de.coinor.rules.Executor;
import de.coinor.rules.Language;
import de.coinor.rules.Rule;
import de.coinor.rules.groovy.GroovyExecutor;

public class JavaDslTestCase {

	private static final String FORMEL = ".formel";

	@Test
	public void testNettoeinkommen() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 2300.0);
		p.put("Steuern", 210.0);
		p.put("Sozialabgaben", 400.0);
		p.put("sonstigeBelastungen", 160.0);
		
		Object result = runDsl("monatlichesNettoeinkommen.formel", p);
		
		assertEquals(Double.valueOf(1530.0), result);
	}
	
	/*
	@Test
	public void testDelta1() throws CompilationFailedException, IOException {
		ImmobilieObject im = new ImmobilieObject();
		im.setMonatlicheMiete(430.0);
		im.setSelbstgenutzt(true);
		im.setBezugsfertig(true);
		
		AntragstellerObject an = new AntragstellerObject();
		an.setMonatlichesNettoeinkommen(1530.0);

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("Immobilie", im);
		p.put("Antragsteller", an);
		p.put("Kreditrate", 270.0);
		
		Object result = runDsl("delta.formel", p);
		
		assertEquals(Double.valueOf(1260.0), result);
	}
	
	@Test
	public void testDelta2() throws CompilationFailedException, IOException {
		ImmobilieObject im = new ImmobilieObject();
		im.setMonatlicheMiete(430.0);
		im.setSelbstgenutzt(false);
		im.setBezugsfertig(true);
		
		AntragstellerObject an = new AntragstellerObject();
		an.setMonatlichesNettoeinkommen(1530.0);

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("Immobilie", im);
		p.put("Antragsteller", an);
		p.put("Kreditrate", 270.0);
		
		Object result = runDsl("delta.formel", p);
		
		assertEquals(Double.valueOf(1690.0), result);
	}*/
	
	@Test
	public void testEinkuenfteRentner() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 0.0);
		p.put("Steuern", 150.0);
		p.put("Sozialabgaben", 200.0);
		p.put("sonstigeBelastungen", 0.0);
		p.put("Beruf", "Renter");
		p.put("monatlichesRentenzahlungen", 1700.0);
		p.put("monatlichesPensionszahlungen", 0.0);
		
		Object result = runDsl("einkuenfte.formel", p);
		
		assertEquals(Double.valueOf(1550.0), result);
	}
	
	@Test
	public void testEinkuenftePensionaer() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 0.0);
		p.put("Steuern", 350.0);
		p.put("Sozialabgaben", 200.0);
		p.put("sonstigeBelastungen", 0.0);
		p.put("Beruf", "Pensionär");
		p.put("monatlichesRentenzahlungen", 0.0);
		p.put("monatlichesPensionszahlungen", 3200.0);
		
		Object result = runDsl("einkuenfte.formel", p);
		
		assertEquals(Double.valueOf(2650.0), result);
	}
	
	@Test
	public void testEinkuenfteStudent() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 400.0);
		p.put("Steuern", 0.0);
		p.put("Sozialabgaben", 0.0);
		p.put("sonstigeBelastungen", 150.0);
		p.put("Beruf", "Student");
		p.put("monatlichesRentenzahlungen", 0.0);
		p.put("monatlichesPensionszahlungen", 0.0);
		
		Object result = runDsl("einkuenfte.formel", p);
		
		assertEquals(Double.valueOf(250.0), result);
	}
	
	@Test
	public void testEinkuenfteAngestellter() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 2300.0);
		p.put("Steuern", 210.0);
		p.put("Sozialabgaben", 400.0);
		p.put("sonstigeBelastungen", 160.0);
		p.put("Beruf", "Angestellter");
		p.put("monatlichesRentenzahlungen", 0.0);
		p.put("monatlichesPensionszahlungen", 0.0);
		
		Object result = runDsl("einkuenfte.formel", p);
		
		assertEquals(Double.valueOf(1530.0), result);
	}
	
	@Test
	public void testEinkuenfteBeamter() throws CompilationFailedException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 2300.0);
		p.put("Steuern", 210.0);
		p.put("Sozialabgaben", 00.0);
		p.put("sonstigeBelastungen", 460.0);
		p.put("Beruf", "Beamter");
		p.put("monatlichesRentenzahlungen", 0.0);
		p.put("monatlichesPensionszahlungen", 0.0);
		
		Object result = runDsl("einkuenfte.formel", p);
		
		assertEquals(Double.valueOf(1630.0), result);
	}

	Object runDsl(String name, Map<String, Object> properties) throws CompilationFailedException, IOException {
		Binding b = new Binding();
		for (Entry<String, Object> entry : properties.entrySet()) {
			b.setVariable(entry.getKey(), entry.getValue());
		}
		
		CompilerConfiguration conf = new CompilerConfiguration();
		conf.setScriptBaseClass(DslDefinition.class.getName());
		GroovyShell gs = new GroovyShell(b, conf);
		
		Script s = gs.parse(new File(name));
		
		return s.run();
	}
	
	@Test
	public void testFormelIntegration() throws FileNotFoundException, IOException {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("monatlichesBruttoeinkommen", 2300.0);
		p.put("Steuern", 210.0);
		p.put("Sozialabgaben", 00.0);
		p.put("sonstigeBelastungen", 460.0);
		p.put("selbstgenutzt", true);
		p.put("bezugsfertig", true);
		p.put("monatlicheMiete", 430.0);
		p.put("Kreditrate", 270.0);
		
		String[] formelFiles = new File("./").list(new FilenameFilter() {
			
			@Override
			public boolean accept(File directory, String filename) {
				return filename.endsWith(FORMEL);
			}
		});
		
		Map<String, Rule> formulas = new HashMap<String, Rule>();
		Language language = new Language("Groovy", GroovyExecutor.class);
		for (String formelFile : formelFiles) {
			String code = IOUtils.toString(new FileInputStream(new File(formelFile)));
			String formulaName = formelFile.replace(FORMEL, "");
			formulas.put(formulaName, new Rule(language, code));
		}
		
		for (int i = 0; i < 100; i++) {
			DslCreationCallback creationCallback = new DslCreationCallback(DslDefinition.class.getName());
			Executor executor = formulas.get("delta").executor(
					creationCallback);
			new Dslifier(executor.getNativeExecutorObject()).dslify(p, formulas, creationCallback);
			Object result = executor.execute();
			
			assertEquals(1360.0, result);
		}
	}
}
