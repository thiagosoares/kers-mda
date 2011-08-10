package br.com.capanema.kers.core.util;

import java.util.List;

import br.com.capanema.kers.common.model.project.ProjectConfig;
import br.com.capanema.kers.common.model.template.Crud;
import br.com.capanema.kers.common.model.template.CrudClass;
import br.com.capanema.kers.common.model.template.CrudField;

public class CrudUtil {
	public static Crud findCrudByClass(String className, ProjectConfig configSpider) {
		Crud crud = null;
		java.util.List<Crud> cruds = configSpider.getCruds();
		for (Crud tempCrud : cruds) {
			if (tempCrud.getCrudClass().getFullName().equals(className)) {
				crud = tempCrud;
				break;
			}
		}
		return crud;
	}
    
	public static Crud findCrudByName(String crudName, ProjectConfig configSpider) {
		Crud crud = null;
		java.util.List<Crud> cruds = configSpider.getCruds();
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
	
	public static CrudField getFieldInCrud(String imethod, CrudClass crudClass) {
		List<CrudField> fields = crudClass.getFields();
		for (CrudField field : fields) {
			if (field.makeGetMethodName().equals(imethod)) {
				return field;
			}
		}
		
		return null;
	}
}
