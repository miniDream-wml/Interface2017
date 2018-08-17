package com.bdtt.rest.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangqiliang on 2017/8/9.
 * 此函数的作用是构建一个包含公共参数的的params格式文件
 */
public class ParamsBuilt {
	
	private static String region_id=PropertiesHandle.readValue("region_id");
	private static String uuid=PropertiesHandle.readValue("uuid");
	private static String udid=PropertiesHandle.readValue("udid");
	private static String android_iOS=PropertiesHandle.readValue("android_iOS");
	private static String version=PropertiesHandle.readValue("version");
	private static String time=PropertiesHandle.readValue("time");
	private static String security=PropertiesHandle.readValue("security");
	//private static String uid=PropertiesHandle.readValue("uid");
	
    public static Map<String, String> params_built() {
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("region_id", region_id);
        params.put("uuid", uuid);
        params.put("udid", udid);
        params.put("android_iOS", android_iOS);
        params.put("version", version);
        params.put("time", time);
        params.put("security", security);
        //params.put("uid", uid);
        return params;
    }
   
    public static void main(String[] args) {
        System.out.println(params_built());
    }
}
