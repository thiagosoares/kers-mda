/*******************************************************************************
 * Copyright (c) 2006 Bruno G. Braga.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno G. Braga - initial API and implementation
 *******************************************************************************/
package org.j2eespider.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.j2eespider.ide.data.domain.ConfigSpider;
import org.j2eespider.ide.data.domain.TemplateDataGroup;
import org.j2eespider.ide.data.domain.TemplateFile;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.editors.ConfigurationEditor;

//import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

public class DatabaseUtil {
	
	public static Table[] getTables(ConfigSpider configSpider) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {		
		//Database settings
		TemplateDataGroup dataGroup = new TemplateDataGroup();
        //database driver, dialect, connection string, etc
		Map databaseTypeConfig = new HashMap();
		databaseTypeConfig = (Map<String, Object>)XmlManager.loadTemplateCodeXml(configSpider.getTemplateFolder(), XML_TYPE.DATABASE_TYPE_CONFIG);
		dataGroup.setDatabaseTypeConfig(databaseTypeConfig);
		dataGroup.setConfig(ConfigurationEditor.getConfigSpider());
		dataGroup.setBundle(PropertiesUtil.readTemplatePropertiesToMap());		
		
		String dbType = configSpider.getTech("Database Type");
		String dbDriver = dataGroup.getConfigForDatabaseType(dbType+"-driver");
		String dbUrl = dataGroup.getConfigForDatabaseType(dbType+"-url");
		
		//Load jars from Template (templateFiles-jar.xml)
		Map<String, List<TemplateFile>> templateJars = (Map<String, List<TemplateFile>>)XmlManager.loadTemplateCodeXml(configSpider.getTemplateFolder(), XML_TYPE.TEMPLATE_FILES_JAR);
		String keyJar = "TECH.DATABASE TYPE."+dbType.toUpperCase()+".JAR";
		//path template
		String pathTemplate = BuildManagerUtil.getPathFolderTemplates()+File.separator+configSpider.getTemplateFolder();
		//path jar
		String pathJar = BuildManagerUtil.getFullPathTemplateFile((templateJars.get(keyJar)).get(0).getPathTemplateFile(), pathTemplate);
		
		Properties connectionProps = new Properties();
		connectionProps.setProperty("user", configSpider.getDbUsername());
		connectionProps.setProperty("password", configSpider.getDbPassword());

		Driver driver = getDriverFromPath(pathJar, dbDriver);
		Connection connection = driver.connect(dbUrl, connectionProps);
	    
		SchemaCrawlerOptions options = new SchemaCrawlerOptions();
	    options.setShowStoredProcedures(false);
	    options.setAlphabeticalSortForTableColumns(true);
	    
		//Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);
		//for (Schema schema: catalog.getSchemas()) {
		//	return schema.getTables();
		//}
		
		return null;
	}
	
	public static Driver getDriverFromPath(String path, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException {
		URL[] url = {new File(path).toURL()};

		URLClassLoader loader = new URLClassLoader(url);

		Class c = loader.loadClass(className);
		Object instance = c.newInstance();
		
		return (Driver)instance;
	}
	
}
