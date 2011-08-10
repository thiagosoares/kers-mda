package org.j2eespider.util.xstream.converters;

import java.util.HashMap;
import java.util.Map;

import org.j2eespider.ide.data.domain.TemplateFileIncrement;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IncrementConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return clazz.equals(TemplateFileIncrement.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {		
		TemplateFileIncrement increment = (TemplateFileIncrement)value;
		String pattern = null;
		if (increment.getParams() != null && increment.getParams().size() > 0) {
			pattern = "\n    "+increment.getPattern()+"\n    ";
		} else {
			pattern = "\n    "+increment.getPattern()+"\n  ";
		}
		
        writer.addAttribute("path", increment.getPath());
        writer.setValue(pattern);
        writeFirstAfter(increment.getFirstAfter(), context, writer);
        writeParams(increment.getParams(), context, writer);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		TemplateFileIncrement increment = new TemplateFileIncrement();
		
		String path = reader.getAttribute("path").toString().trim();
		increment.setPath(path);
		
		String pattern = reader.getValue().toString().trim();
		increment.setPattern(pattern);
		
		Map<String, String> params = new HashMap<String, String> ();
		String firstIncrementAfter = readNodes(reader, context, params);
		
		increment.setParams(params);
		increment.setFirstAfter(firstIncrementAfter);
		
		return increment;
	}

    protected String readNodes(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
    	String firstIncrementAfter = null;
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            
            if (reader.getNodeName().equals("param")) {
                Object key = reader.getAttribute("name");
                Object value = reader.getValue();
                map.put(key, value);
            } else if (reader.getNodeName().equals("firstIncrementAfter")) {
            	Object value = reader.getValue();
            	if (value != null) {
            		firstIncrementAfter = value.toString().trim();
            	}
            }

            reader.moveUp();
        }
        
        return firstIncrementAfter;
    }
  
    protected void writeFirstAfter(String firstAfter, MarshallingContext context, HierarchicalStreamWriter writer) {
    	if (firstAfter == null || firstAfter.equals("")) {
    		return;
    	}
    	
        writer.startNode("firstIncrementAfter");
        writer.setValue(firstAfter);
        writer.endNode();
    }
    
    protected void writeParams(Map<String, String> params, MarshallingContext context, HierarchicalStreamWriter writer) {
    	if (params == null || params.size() == 0) {
    		return;
    	}
    	for (String name : params.keySet()) {
            writer.startNode("param");
            writer.addAttribute("name", name);
            writer.setValue(params.get(name));
            writer.endNode();
    	}
    }
	
}
