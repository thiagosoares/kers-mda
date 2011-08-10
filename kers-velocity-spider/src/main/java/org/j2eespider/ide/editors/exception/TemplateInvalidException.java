package org.j2eespider.ide.editors.exception;

public class TemplateInvalidException extends Exception {
	private String[] params;
	
	public TemplateInvalidException(String key, String[] params) {
		super(key);
		this.params = params;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}
}
