package org.j2eespider.util;

import java.util.List;

//import org.eclipse.jdt.core.IMethod;
import org.j2eespider.ide.data.domain.Crud;
import org.j2eespider.ide.data.domain.CrudClass;
import org.j2eespider.ide.data.domain.CrudField;
import org.j2eespider.ide.editors.ConfigurationEditor;

public class CrudUtil {
	public static Crud findCrudByClass(String className) {
		Crud crud = null;
		java.util.List<Crud> cruds = ConfigurationEditor.getConfigSpider().getCruds();
		for (Crud tempCrud : cruds) {
			if (tempCrud.getCrudClass().getFullName().equals(className)) {
				crud = tempCrud;
				break;
			}
		}
		return crud;
	}
    
	public static Crud findCrudByName(String crudName) {
		Crud crud = null;
		java.util.List<Crud> cruds = ConfigurationEditor.getConfigSpider().getCruds();
		for (Crud tempCrud : cruds) {
			if (tempCrud.getName().equals(crudName)) {
				crud = tempCrud;
				break;
			}
		}
		return crud;
	}
	
	public static String getClassNameFromClassPath(String classPath) {
		String[] temp = classPath.split("\\.");
		String name = temp[temp.length-1];
		
		return name;
	}
	
	/*public static CrudField getFieldInCrud(IMethod imethod, CrudClass crudClass) {
		List<CrudField> fields = crudClass.getFields();
		for (CrudField field : fields) {
			if (field.makeGetMethodName().equals(imethod.getElementName())) {
				return field;
			}
		}
		
		return null;
	}*/
}
