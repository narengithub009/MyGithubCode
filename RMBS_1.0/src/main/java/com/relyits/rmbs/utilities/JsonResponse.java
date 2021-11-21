package com.relyits.rmbs.utilities;

import java.util.HashMap;
import java.util.Map;

public class JsonResponse {
	private String flag = null;
	private Object result = null;
	private Map<String, Object> model;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Map<String, Object> getModel() {
		return model;
	}
	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	
}
