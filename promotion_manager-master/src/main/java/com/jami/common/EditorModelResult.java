package com.jami.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lei on 2015/7/20.
 */
public class EditorModelResult {



    public Map<String, Object> getModelMap() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        /*modelMap.put("errCode", errCode);
        modelMap.put("retCode", retCode);
        modelMap.put("msg", StringUtils.isEmpty(msg) ? "" : msg);
        modelMap.put("dtag", StringUtils.isEmpty(dtag) ? "" : dtag);
        modelMap.put("data", data == null ? new JSONObject() : data);*/
        return modelMap;
    }
}
