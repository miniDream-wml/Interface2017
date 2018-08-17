package com.bdtt.rest.util;

import net.sf.json.JSONObject;

import java.sql.SQLException;

import static com.bdtt.rest.util.GetMySql.*;


/**
 * 此函数的作用是构建一个包含baserequest的json格式文件
 */
public class JSonBuilt {
	private static String region_id=PropertiesHandle.readValue("region_id");
	private static String uuid=PropertiesHandle.readValue("uuid");
	private static String udid=PropertiesHandle.readValue("udid");
	private static String android_iOS=PropertiesHandle.readValue("android_iOS");
	private static String version=PropertiesHandle.readValue("version");
	private static String time=PropertiesHandle.readValue("time");
	private static String security=PropertiesHandle.readValue("security");
	//private static String uid=PropertiesHandle.readValue("uid");

    public static JSONObject json_built() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("region_id", region_id);
        jsonParam.put("uuid",uuid);
        jsonParam.put("udid",udid);
        jsonParam.put("android_iOS",android_iOS);
        jsonParam.put("version",version);
        jsonParam.put("time",time);
        jsonParam.put("security",security);
        //jsonParam.put("uid",uid);
        return jsonParam;
    }

/*    
    public static JSONObject json_built_pla() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JSONObject jsonParam = json_built();
        jsonParam.put("openId",openId);
        jsonParam.put("systemId",systemId);
        jsonParam.put("platformType",platformType);
        jsonParam.put("signature",signature);
        return jsonParam;
    }
*/    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        System.out.println(json_built());
    }
}
