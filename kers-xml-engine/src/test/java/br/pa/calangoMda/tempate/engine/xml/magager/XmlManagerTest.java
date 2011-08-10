package br.pa.calangoMda.tempate.engine.xml.magager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.com.capanema.kers.common.model.repository.Repository;
import br.com.capanema.kers.common.model.template.TemplateSumary;
import br.com.capanema.kers.tempate.engine.xml.magager.XmlManager;

import com.google.common.collect.Multimaps;

public class XmlManagerTest {

	
	@Test
	public void export() {
		
		
		//System.out.println(XmlManager.getXStream().toXML(new Testa()));
	  TemplateSumary su = new TemplateSumary();
	  su.setCompatibilityDate("con");
	  su.setDefault(false);
	  su.setDescription("con");
	  su.setFolder("con");
	  su.setName("con");
		
	  List<TemplateSumary> lista = new ArrayList<TemplateSumary>();
	  lista.add(su);
	  lista.add(su);
	  
	  Repository repo = new Repository();
	  repo.setName("nomeee");
	  repo.setPath("muiraquita_def");
	  repo.setSumaries(lista);

	  //System.out.println(XmlManager.getXStream().toXML(repo));
	  
	  
	  Map<String, List<TemplateSumary>> mapa = new HashMap<String, List<TemplateSumary>>(); 
	  mapa.put("K1", lista);
		
		System.out.println(XmlManager.getXStream().toXML(mapa));
		
		
		
	}
	
	
	
	@Test
	public void testWriteXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadXmlStringXmlType() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadXmlInputStreamXmlType() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadXmlFromString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadXmlFromUrl() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadTemplateCodeXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetXStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetXStreamXmlType() {
		fail("Not yet implemented");
	}

	private class Testa {

	  String s;
	  
	  List<Testa> lista = new ArrayList<Testa>();
	  
	  Map<String, String> obj = new LinkedHashMap<String, String>();
	  
	  Map<String, List<String>> obj2 = new LinkedHashMap<String, List<String>>();

	  com.google.common.collect.HashMultimap<String, String> multmap;
	  
    public Testa() {
      super();
      obj.put("k1","v1");
      obj.put("k2","v2");
      
      List<String> l1 = new ArrayList<String>();
      l1.add("l1");
      l1.add("l2");
      
      obj2.put("k1",l1);
      obj2.put("k2",l1);
      
      multmap = com.google.common.collect.HashMultimap.create();
      multmap.put("A", "A");
      multmap.put("A", "B");
      multmap.put("B", "A");
      multmap.put("B", "B");
    }
    
    public Testa(String s) {
       this.s = s;
    }

    public Map<String, String> getObj() {
      return obj;
    }

    public void setObj(Map<String, String> obj) {
      this.obj = obj;
    }

    public Map<String, List<String>> getObj2() {
      return obj2;
    }

    public void setObj2(Map<String, List<String>> obj2) {
      this.obj2 = obj2;
    }

    public com.google.common.collect.HashMultimap<String, String> getMultmap() {
      return multmap;
    }

    public void setMultmap(com.google.common.collect.HashMultimap<String, String> multmap) {
      this.multmap = multmap;
    }

    public List<Testa> getLista() {
      return lista;
    }

    public void setLista(List<Testa> lista) {
      this.lista = lista;
    }

    
    
	  
	  
	  
	  
	}
}
	
