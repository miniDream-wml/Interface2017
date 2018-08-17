package com.bdtt.rest.util;

import com.bdtt.rest.util.PropertiesHandle;

public class  UrlAdd {
	private static String region_id=PropertiesHandle.readValue("region_id");
	private static String uuid=PropertiesHandle.readValue("uuid");
	private static String udid=PropertiesHandle.readValue("udid");
	private static String android_iOS=PropertiesHandle.readValue("android_iOS");
	private static String version=PropertiesHandle.readValue("version");
	private static String time=PropertiesHandle.readValue("time");
	private static String security=PropertiesHandle.readValue("security");
	private static String bdread=PropertiesHandle.readValue("bdread");
	private static String kongread=PropertiesHandle.readValue("kongread");
	//private static String uid=PropertiesHandle.readValue("uid");

    
    public static String UrlAdd(){
    	
    	//String url="?deviceType="+deviceType+"&systemVersion="+systemVersion+"&appVersion="+appVersion+"&clientIP="+clientIP+"&deviceNumber="+deviceNumber+"&token="+token+"&sessionId="+sessionId+"&shopId="+shopId;
    	    	
    	//String url="?bdread="+bdread;//非网关
    	String url="?bdread="+bdread+"&kongread="+kongread;//网关
    	    			
    	return url;
    }
    
    public static String UrlAdd_json(){
    	
    	//String url="?deviceType="+deviceType+"&systemVersion="+systemVersion+"&appVersion="+appVersion+"&clientIP="+clientIP+"&deviceNumber="+deviceNumber+"&token="+token+"&sessionId="+sessionId+"&shopId="+shopId;
    	    	
    	String url="?bdread="+bdread+"&region_id="+region_id+"&uuid="+uuid+"&udid="+udid+"&android_iOS="+android_iOS+"&version="+version+"&time="+time+"&security="+security;
    	    			
    	return url;
    }
    
}
