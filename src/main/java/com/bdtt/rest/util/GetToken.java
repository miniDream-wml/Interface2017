package com.bdtt.rest.util;

import com.google.gson.Gson;
import com.bdtt.rest.util.HTTPPost;
import com.bdtt.rest.util.PropertiesHandle;
import com.bdtt.rest.util.TestJSonResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Created by tangqiliang on 2017/11/23.
 * 通过调用“登录”接口来获取token值
 */

public class GetToken {
	
	static String testUrl = "account/login";
	static String baseUrl = PropertiesHandle.readValue("accountbaseUrl");
	static String username = PropertiesHandle.readValue("username");
	static String password = PropertiesHandle.readValue("password");
	
	public static String getToken() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
    	
    	String client_id="bdttapp";//服务器分配
    	String client_secret="10740fa78ba59d9302ff5c29a3e5616c";//服务器分配
    	String grant_type="password";
    	//String login_uid=" ";//马甲相关账号此项必传，不需要传 username 和 password
        //System.out.println(url);
       
    	Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", grant_type);
        params.put("username", username);
        params.put("password", password);
        //params.put("login_uid", login_uid);
        //System.out.println(params);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;

        String token = dataResult.getString("access_token");
        //System.out.println(token);
        
        return token;
	}	
	
	public static String getToken(String username, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {	
    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
    	url = url.replaceAll("\\\\", "");
    
    	String client_id="bdttapp";//服务器分配
    	String client_secret="10740fa78ba59d9302ff5c29a3e5616c";//服务器分配
    	String grant_type="password";
    	//String login_uid=" ";//马甲相关账号此项必传，不需要传 username 和 password
       
    	Map<String, String> params = ParamsBuilt.params_built(); 
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", grant_type);
        params.put("username", username);
        params.put("password", password);
        //params.put("login_uid", login_uid);
        //System.out.println(params);

        String result = HTTPPost.httpPostForm(url, params);
        System.out.println(result);
                
        Gson gs = new Gson();
        TestJSonResult final_res = gs.fromJson(result, TestJSonResult.class);
        JSONObject dataResult = final_res.data;

        String token = dataResult.getString("access_token");
        //System.out.println(token);
        
        return token;
	}	
	
	public static void main (String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, IOException {
		GetToken.getToken();
	}
}


