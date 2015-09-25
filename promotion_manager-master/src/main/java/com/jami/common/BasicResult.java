package com.jami.common;


import com.jami.exception.BusinessException;

/**
 * 接口调用结果的基本类
 *
 * @author rickwang
 */
public class BasicResult {

    /**
     * 错误码. 0表示成功, 其它表示失败
     */
    protected int errCode = 0;

    /**
     * 业务返回码. 当errCode为0时，本字段可以为各种有业务意义的取值. 也可以不细分跟errCode相同
     */
    protected int retCode = 0;

    /**
     * 当errCode不为0时, 表示错误内容
     */
    protected String msg = null;

    /**
     * 用于调用者对应答和请求进行匹配. 由调用者在请求中传递给服务端, 服务端原样在应答中返回
     */
    protected String dtag = null;

    /**
     * 其它数据
     */
    protected Object data;  //可以是Map数据，也可以是一个JSON对象


    public BasicResult() {
    }

    public void setResult(int errCode, int retCode, String msg) {
		this.errCode = errCode;
		this.retCode = retCode;
		this.msg = msg;
	}

	public BasicResult(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    public static BasicResult successResult() {
        return new BasicResult(0, null);
    }

    public static BasicResult successResult(String msg) {
        return new BasicResult(0, msg);
    }

    public static BasicResult errorResult(int errCode) {
        return new BasicResult(-1, null);
    }

    public static BasicResult errorResult(int errCode, String msg) {
        return new BasicResult(-1, msg);
    }
    
    public static BasicResult errorResult(BusinessException e) {
		return new BasicResult(e.getErrCode(), e.getErrMsg());
	}
    
    public void setErrInfo(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDtag() {
        return dtag;
    }

    public void setDtag(String dtag) {
        this.dtag = dtag;
    }

	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
