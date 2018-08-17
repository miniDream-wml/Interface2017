package com.bdtt.rest.util;

import net.sf.json.JSONObject;

public class TestJSonResult {
	
	public int code;
    public JSONObject data;
    public String msg;
    public Boolean success;
	
    public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	public String getMessage() {
		return msg;
	}
	public void setMessage(String msg) {
		this.msg = msg;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public JSONObject getData() {
		return data;
	}

}
