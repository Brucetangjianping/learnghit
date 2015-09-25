package com.jami.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by felixzhao on 14-5-14.
 */
@Component
public class Config {

    private Properties properties;

    @Value("cfg_${cfg.env}.properties")
    private String propsFileName;

    @PostConstruct
    public void init(){
        try
        {
            // 加载main.properties文件
            properties = new Properties();
            InputStream in = Config.class.getClassLoader()
                    .getResourceAsStream(propsFileName);
            properties.load(in);
        } catch (Exception e)
        {
            Log.run.error("main.properties文件加载失败！", e);
        }
    }

    public String getString(String key){
       return properties.getProperty(key);
    }

    public String getString(String key, String defaultValue){
        String v = properties.getProperty(key);
        if (StringUtils.isEmpty(v)){
            return  defaultValue;
        }else {
            return v;
        }

    }
}
