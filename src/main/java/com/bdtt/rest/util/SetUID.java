package com.bdtt.rest.util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by tangqiliang on 2017/8/9.
 * 从数据库获取uid并写入配置文件config.properties中
 */

public class SetUID {
	
    public static void setUID(String key, String value) {//修改指定的key
        Properties prop = new Properties();
        OutputStream w = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            w = new FileOutputStream("config.properties");
            prop.setProperty(key, value);
            prop.store(w, key);
            w.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(null != w) {
                    w.close();
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
    	String username = PropertiesHandle.readValue("username");
    	System.out.println(username);
    	String uid = GetMySql.getUserId(username);
    	System.out.println(uid);	
    	setUID("uid", uid);
    }

}

