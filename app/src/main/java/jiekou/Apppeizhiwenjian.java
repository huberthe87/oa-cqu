package jiekou;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import android.content.Context;

public class Apppeizhiwenjian {
	static String pro_value;  
	  
    public static String getProperties(Context c, String proName){  
        Properties props = new Properties();  
        try {  
        //方法一：通过activity中的context攻取setting.properties的FileInputStream  
        InputStream in = c.getAssets().open("myproperty.properties");  
        props.load(new InputStreamReader(in,"utf-8"));  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        pro_value = props.getProperty(proName); 
        System.out.println(pro_value);
        return pro_value;  
        }  
}