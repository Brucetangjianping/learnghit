package com.jami.exception;


/**
 * Created by felixzhao on 14-2-17.
 */
public class BusinessException extends Exception {
    private static final long serialVersionUID = 7700902241922267190L;

    private int errCode;
    private String errMsg;
    private Exception targetException;

    public BusinessException(){
    }
    public static void main(String[] args) throws Exception {
    }
    public BusinessException(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BusinessException(int errCode, String errMsg, Exception e){
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.targetException = e;
    }

    public static BusinessException createInstance(int errorCode, String errorMsg) {
        return new BusinessException(errorCode, errorMsg);
    }

    public static BusinessException createInstance(BizErrorCode bizErrorCode) {
        return new BusinessException(bizErrorCode.getCode(), bizErrorCode.getMsg());
    }

    public static BusinessException createInstance(BizErrorCode bizErrorCode, Exception e) {
        return new BusinessException(bizErrorCode.getCode(), bizErrorCode.getMsg(), e);
    }

    public static BusinessException createInstance(BizErrorCode bizErrorCode, String errMsg) {
        return new BusinessException(bizErrorCode.getCode(), "[" + bizErrorCode.getMsg() + "]" + errMsg);
    }

    public static BusinessException createInstance(BizErrorCode bizErrorCode, String errMsg, Exception e) {
        return new BusinessException(bizErrorCode.getCode(), "[" + bizErrorCode.getMsg() + "]" + errMsg, e);
    }

    public static BusinessException createInstance(int errorCode, String errorMsg, Exception e) {
        return new BusinessException(errorCode, errorMsg, e);
    }

    public static BusinessException createEmptyInstance() {
        return new BusinessException();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusinessException{");
        sb.append("errCode=").append(errCode);
        sb.append(", errMsg='").append(errMsg).append('\'');
        sb.append(", targetException=").append(targetException);
        sb.append('}');
        return sb.toString();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Exception getTargetException() {
        return targetException;
    }

    public void setTargetException(Exception targetException) {
        this.targetException = targetException;
    }
}
