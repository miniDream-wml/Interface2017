package com.bdtt.rest.util;

import java.util.Map;

public class RegisterUser {
	
	public static void registerUser(String username, String password, String env) {
		
		if (env == "test") {
			String testUrl="d-account/account/register";
			String baseUrl="http://app-test.benditoutiao.com/";
			String client_id="bdttapp";
			String client_secret="10740fa78ba59d9302ff5c29a3e5616c";
			String grant_type="password";
			
	    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
	    	url = url.replaceAll("\\\\", "");
	        //System.out.println(url);
	        
	    	Map<String, String> params = ParamsBuilt.params_built(); 
	        params.put("client_id", client_id);
	        params.put("client_secret", client_secret);
	        params.put("grant_type", grant_type);
	        params.put("username", username);
	        params.put("password", password);
	        //System.out.println(params);

	        HTTPPost.httpPostForm(url, params);
			
		} else if (env == "pre") {
			
			String testUrl="d-account/account/register";
			String baseUrl="http://new-build.benditoutiao.com/";
			String client_id="bdttapp";
			String client_secret="10740fa78ba59d9302ff5c29a3e5616c";
			String grant_type="password";
			
	    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
	    	url = url.replaceAll("\\\\", "");
	        //System.out.println(url);
	        
	    	Map<String, String> params = ParamsBuilt.params_built(); 
	        params.put("client_id", client_id);
	        params.put("client_secret", client_secret);
	        params.put("grant_type", grant_type);
	        params.put("username", username);
	        params.put("password", password);
	        //System.out.println(params);

	        HTTPPost.httpPostForm(url, params);

		} else if (env == "online") {
			
			String testUrl="d-account/account/register";
			String baseUrl="http://app.benditoutiao.com/";
			String client_id="bdttapp";
			String client_secret="10740fa78ba59d9302ff5c29a3e5616c";
			String grant_type="password";
			
	    	String url = baseUrl + testUrl + UrlAdd.UrlAdd();
	    	url = url.replaceAll("\\\\", "");
	        //System.out.println(url);
	        
	    	Map<String, String> params = ParamsBuilt.params_built(); 
	        params.put("client_id", client_id);
	        params.put("client_secret", client_secret);
	        params.put("grant_type", grant_type);
	        params.put("username", username);
	        params.put("password", password);
	        //System.out.println(params);

	        HTTPPost.httpPostForm(url, params);
			
		} else {
			System.out.println("env变量不对，应该是test, pre 或者 online");
		}
		
	}

}
