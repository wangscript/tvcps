package com.j2ee.cms.sys;

import java.util.ResourceBundle;

public class Tools {
    public static String getConfPropertyValueByParam(String str){
        try{
            ResourceBundle bun = ResourceBundle.getBundle("conf");
            return bun.getString(str);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
