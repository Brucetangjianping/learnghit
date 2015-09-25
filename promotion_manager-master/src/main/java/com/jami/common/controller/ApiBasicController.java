package com.jami.common.controller;

import com.alibaba.fastjson.JSON;
import com.jami.common.JsonModelResult;
import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.jami.util.Log;


/**
 * API类型的基础controller
 * Created by felixzhao on 14-2-24.
 */
public class ApiBasicController extends JamiBasicController {

    /*protected void handleException(JsonModelResult result, Exception e) {
        if (e instanceof BusinessException) {
            BusinessException exp = (BusinessException)e;

        } else {
            result.setErrInfo(BizErrorCode.UNKNOW_ERROR.getCode(), e.getMessage());
        }
        Log.run.warn("exception==>" + e.toString(), e);
    }

    protected String obj2Json(Object obj) {
        if (obj != null) {
            return JSON.toJSONString(obj).toString();
        }
        return null;
    }*/

}
