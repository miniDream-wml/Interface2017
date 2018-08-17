package com.bdtt.rest.util;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Created by tangqiliang on 2017/11/23.
 * 通过调用SetToken中的setToken()方法获取token值，并写入配置文件config.properties中
 */

public class SetToken {
	
    public static void setToken(String key, String value) {//修改指定的key
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
    	String token = GetToken.getToken();
    	System.out.println(token);	
    	setToken("token", token);
    }

}

