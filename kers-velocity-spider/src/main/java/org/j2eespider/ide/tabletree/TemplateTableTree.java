package org.j2eespider.ide.tabletree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
/*import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
*/import org.j2eespider.SpiderPlugin;
import org.j2eespider.ide.data.domain.Repository;
import org.j2eespider.ide.data.domain.RepositoryLink;
import org.j2eespider.ide.data.domain.RepositoryTemplateInfo;
import org.j2eespider.ide.data.rules.XmlManager;
import org.j2eespider.ide.data.rules.XmlManager.XML_TYPE;
import org.j2eespider.ide.editors.ConfigurationEditor;
//import org.j2eespider.ide.editors.pages.TemplatePage;
import org.j2eespider.ide.editors.pages.PageName.PAGE_NAME;
import org.j2eespider.ide.jobs.DownloadTemplateJob;
import org.j2eespider.ide.jobs.LoadTemplateRepositoryJob;
import org.j2eespider.util.DialogUtil;
import org.j2eespider.util.PropertiesUtil;
import org.j2eespider.util.StringUtil;

public class TemplateTableTree { //extends Grid {
	//private Image imgUnchecked, imgChecked, imgCheckedDisable;
	private Map<String, RepositoryTemplateInfo> mapTemplateInfo = new HashMap<String, RepositoryTemplateInfo>();
	private Map<String, List> mapUrlTemplateRequired = new HashMap<String, List>();
	//private Map<String, String> mapItemToUrl = new HashMap<String, String>();
	//private Map<String, GridItem> mapUrlToItem = new HashMap<String, GridItem>();
	
	//constants
	public static final int COLUMN_URL_INFO = 4;
	public static final int COLUMN_CHECKED = 5;
	public static final int COLUMN_SIZE_IN_BYTES = 6;
	public static final int COLUMN_URL_ZIP = 7;
	
	/*public TemplateTableTree(Composite composite, int style) {
		super(composite, style);

		imgUnchecked = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_unchecked.gif"));
		imgChecked = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_checked.gif"));
		imgCheckedDisable = new Image(composite.getDisplay(), getClass().getResourceAsStream("/images/icon_checked_disable.gif"));
	}*/

	public void init() {
		/*this.setHeaderVisible(true);
		this.setLinesVisible(false);
		
		GridColumn column1 = new GridColumn(this, SWT.LEFT);
		column1.setText("Template");
		column1.setWidth(350);
		column1.setTree(true);
		GridColumn column2 = new GridColumn(this, SWT.CENTER);
		column2.setText("Size");
		column2.setWidth(90);
		GridColumn column3 = new GridColumn(this, SWT.CENTER);
		column3.setText("Version");
		column3.setWidth(90);
		GridColumn column4 = new GridColumn(this, SWT.CENTER);
		column4.setText("Date");
		column4.setWidth(100);
		//hidden fields
		GridColumn column5 = new GridColumn(this, SWT.CENTER);
		column5.setText("URL Template Info");
		column5.setWidth(0);
		GridColumn column6 = new GridColumn(this, SWT.CENTER);
		column6.setText("checked");
		column6.setWidth(0);
		GridColumn column7 = new GridColumn(this, SWT.CENTER);
		column7.setText("size in bytes");
		column7.setWidth(0);
		GridColumn column8 = new GridColumn(this, SWT.CENTER);
		column8.setText("URL Template Zip");
		column8.setWidth(0);*/
	}

	/**
	 * Check template selection, mark checkboxes and return description of item
	 * @param item
	 * @return
	 */
	/*public String checkSelection(final GridItem item) {
		String urlTemplateInfo = item.getText(TemplateTableTree.COLUMN_URL_INFO);
		
		if (urlTemplateInfo.equals("")) { //check only templates items
			return "";
		}
		
		String description = "";
		
		if (item.getImage() == imgUnchecked) {
			item.setImage(imgChecked);
			item.setText(5, "true"); //status of "checked" for internal control
			
			RepositoryTemplateInfo templateInfo = mapTemplateInfo.get(urlTemplateInfo);
			
			//set description
			description = templateInfo.getDescription();

			//select dependencies
			List<String> listRequired = mapUrlTemplateRequired.get(urlTemplateInfo);
			for (String urlRequired : listRequired) {
				GridItem itemRequired = mapUrlToItem.get(urlRequired);
				itemRequired.setImage(imgCheckedDisable);
				itemRequired.setText(5, "true"); //status of "checked" for internal control
			}
		} else {
			item.setImage(imgUnchecked);
			item.setText(5, "false"); //status of "checked" for internal control
		}
		
		//deselect all
		try {
			this.setSelection(new int[]{});			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return description;
	}*/    

	/*public List<GridItem> getSelectedItems() {
		ArrayList<GridItem> selectedItems = new ArrayList<GridItem>();
		ArrayList<String> selectedUrls = new ArrayList<String>();
		
		GridItem[] items = getItems();
		//get selected items
		for (GridItem gridItem : items) {
	  		String urlZip = gridItem.getText(TemplateTableTree.COLUMN_URL_ZIP);
	  		String urlTemplateInfo = gridItem.getText(TemplateTableTree.COLUMN_URL_INFO);
	  		String checkedStatus = gridItem.getText(TemplateTableTree.COLUMN_CHECKED);
	  		String strSize = gridItem.getText(TemplateTableTree.COLUMN_SIZE_IN_BYTES);
			
			
			//if item has URL and checked, download!
			if (!urlZip.equals("") && checkedStatus.equals("true")) {
				if (!selectedUrls.contains(urlZip)) { //if not exists yet
					selectedItems.add(gridItem);
					selectedUrls.add(urlZip);					
				}
				
				//get dependencies
				List<String> listRequired = mapUrlTemplateRequired.get(urlTemplateInfo);
				for (String urlRequired : listRequired) {
					GridItem itemRequired = mapUrlToItem.get(urlRequired);
					String urlZipRequired = itemRequired.getText(TemplateTableTree.COLUMN_URL_ZIP);
					
					if (!selectedUrls.contains(urlZipRequired)) { //if not exists yet
						selectedItems.add(itemRequired);
						selectedUrls.add(urlZipRequired);					
					}					
				}				
			}	
		}
		
		return selectedItems;
	}*/
	
	/*public void renderRepositoryItens(List<Repository> repositories) {
		for (Repository repository : repositories) {
			GridItem item = new GridItem(this, SWT.NONE);
			item.setText(repository.getName()); //setting repository name
	
			//getting template info (description)
			List<RepositoryLink> links = repository.getLinks();
			for (RepositoryLink repositoryLink : links) {
				String urlTemplateInfo = repositoryLink.getUrlTemplateInfo();
				RepositoryTemplateInfo templateInfo = getTemplateInfo(urlTemplateInfo);
				
				//adding template to grid
				GridItem subItem = new GridItem(item, SWT.NONE);
				subItem.setText(templateInfo.getName());
				subItem.setImage(imgUnchecked);
				subItem.setText(1, StringUtil.convertSizeToMB(templateInfo.getZipSize()));
				subItem.setText(2, templateInfo.getVersion());
				subItem.setText(3, templateInfo.getCompatibilityDate());
				subItem.setText(4, urlTemplateInfo);
				subItem.setText(5, "false");
				subItem.setText(6, String.valueOf(templateInfo.getZipSize()));
				subItem.setText(7, templateInfo.getUrlMirror());
				
				//adding techs to template (information)
				for (String tech : templateInfo.getTechs()) {
					GridItem s = new GridItem(subItem, SWT.NONE);
					s.setText("- "+tech);						
				}
	
				mapTemplateInfo.put(urlTemplateInfo, templateInfo);
				mapUrlTemplateRequired.put(urlTemplateInfo, repositoryLink.getUrlTemplateInfoRequired());
				//mapItemToUrl.put(subItem.toString(), urlTemplateInfo);
				mapUrlToItem.put(urlTemplateInfo, subItem);
			}				
		}
	}*/
	
   /* public RepositoryTemplateInfo getTemplateInfo(String urlTemplateInfo) {
    	int timeout = SpiderPlugin.getDefault().getRepositoryTimeoutPreference();
    	
		URL url = null;
		try {
			url = new URL(urlTemplateInfo);
		} catch (MalformedURLException e) {
		}
		
		try {
			RepositoryTemplateInfo templateInfo = (RepositoryTemplateInfo)XmlManager.loadXmlFromUrl(url, timeout, XML_TYPE.REPOSITORY_TEMPLATE_INFO);
			
			return templateInfo;
		} catch (IOException e) {
		}
	
		return null;
    }*/

	public Map<String, RepositoryTemplateInfo> getMapTemplateInfo() {
		return mapTemplateInfo;
	}
	
}
