package br.com.capanema.kers.tempate.engine.xml.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Converts a java.util.Map to XML, specifying an 'entry'
 * element with 'key' and 'value' children.
 * <p>Note: 'key' and 'value' is not the name of the generated tag. The
 * children are serialized as normal elements and the implementation expects
 * them in the order 'key'/'value'.</p>
 * <p>Supports java.util.HashMap, java.util.Hashtable and
 * java.util.LinkedHashMap.</p>
 *
 */
public class TemplateFileMapConverter extends AbstractCollectionConverter {
	
    public TemplateFileMapConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return type.equals(HashMap.class)
                || type.equals(Hashtable.class)
                || (JVM.is14() && type.getName().equals("java.util.LinkedHashMap"));
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map map = (Map) source;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            writer.startNode(mapper().serializedClass(Map.Entry.class));
            
            //adicionado key como atributo do entry, para ficar menos verboso e simplificar o xml
            writer.addAttribute("type", entry.getKey().toString()); 
            //writeItem(entry.getKey(), context, writer);
            writeItem(entry.getValue(), context, writer);

            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map map = (Map) createCollection(context.getRequiredType());
        populateMap(reader, context, map);
        return map;
    }

    protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
    	
        while (reader.hasMoreChildren()) {
            reader.moveDown();

            //reader.moveDown();
            Object key = reader.getAttribute("type");
            //reader.moveUp();
        	
            List data = new ArrayList();

        	while (reader.hasMoreChildren()) {
        		reader.moveDown();
                Object value = readItem(reader, context, map);
                reader.moveUp();
                
                data.add(value);
        	}
            
            map.put(key, data);

            reader.moveUp();
        }
    	
/*
        while (reader.hasMoreChildren()) {
            reader.moveDown();

            //reader.moveDown();
            Object key = reader.getAttribute("key");
            //reader.moveUp();

            reader.moveDown();
            Object value = readItem(reader, context, map);
            reader.moveUp();

            map.put(key, value);

            reader.moveUp();
        }
*/        
    }
    
    protected void writeItem(Object item, MarshallingContext context, HierarchicalStreamWriter writer) {
        // PUBLISHED API METHOD! If changing signature, ensure backwards compatability.
        if (item == null) {
            // todo: this is duplicated in TreeMarshaller.start()
            String name = mapper().serializedClass(null);
            writer.startNode(name);
            writer.endNode();
        } else {
            String name = mapper().serializedClass(item.getClass());
        	Mapper.ImplicitCollectionMapping mapping = super.mapper().getImplicitCollectionDefForFieldName(item.getClass(), name);
        	
        	if (mapping == null) {
        		ExtendedHierarchicalStreamWriterHelper.startNode(writer, name, item.getClass());
        	}
        	
            context.convertAnother(item);
            
            if (mapping == null) {
            	writer.endNode();
            }
        }
    }

    protected Object readItem(HierarchicalStreamReader reader, UnmarshallingContext context, Object current) {
        // PUBLISHED API METHOD! If changing signature, ensure backwards compatability.
        String classAttribute = reader.getAttribute(mapper().aliasForAttribute("class"));
        Class type;
        if (classAttribute == null) {
            type = mapper().realClass(reader.getNodeName());
        } else {
            type = mapper().realClass(classAttribute);
        }
        return context.convertAnother(current, type);
    }
    
}
