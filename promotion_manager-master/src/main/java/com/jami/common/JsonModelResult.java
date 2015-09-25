package com.jami.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by felixzhao on 14-5-13.
 */
public class JsonModelResult extends BasicResult {
    public Map<String, Object> getModelMap() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("errCode", errCode);
        modelMap.put("retCode", retCode);
        modelMap.put("msg", StringUtils.isEmpty(msg) ? "" : msg);
        modelMap.put("dtag", StringUtils.isEmpty(dtag) ? "" : dtag);
        modelMap.put("data", data == null ? new JSONObject() : data);
        return modelMap;
    }
}
