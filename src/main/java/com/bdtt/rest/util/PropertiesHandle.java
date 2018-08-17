package com.bdtt.rest.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandle {

	public static String readValue(String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("config.properties"));
            props.load(in);
            String value = props.getProperty(key);
            return value;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
		
}
