package com.bdtt.rest.util;

import java.util.List;

public class TestJSonResultWithListData {
	
	public int code;
    public List data;
    public String msg;
    public Boolean success;
	
    public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setData(List data) {
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
	public List getData() {
		return data;
	}

}
