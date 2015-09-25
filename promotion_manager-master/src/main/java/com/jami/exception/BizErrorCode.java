package com.jami.exception;

public enum BizErrorCode {
	SUCCESS(0, ""),

	UNKNOW_ERROR(0x0100, "未知错误"),

	PARA_ERROR(0x0101, "输入参数错误"),

    HTTP_REQUEST_FAILED(0x0103, "HTTP请求失败"),

    AUTH_TOKEN_IS_EMPTY(0x0104, "鉴权参数为空"),

    AUTH_UIN_IS_NOT_NUMBER(0x0105, "鉴权参数uin非法"),

    AUTH_FAILED(0x0191, "鉴权失败"), // 401

    CALL_THRIFT_SVC_BIZ_FAILED(0x0106, "调用接口服务端失败"),

    CALL_THRIFT_CLIENT_BIZ_FAILED(0x0107, "调用接口客户端端失败"),

    PERMISSION_DENIED(0x108, "没有权限"),
    LOCATION_EXISTED(0x109, "地点已经存在"),
    LOCATION_NOT_EXIST(0x110, "地址不存在"),
    USER_NOT_EXIST(0x111, "用户不存在"),
    PASSWORD_ERROR(0x112, "密码错误"),
	ARTICLE_NOT_EXIST(0x113, "文章不存在"),
	MSG_NOT_EXIST(0x114, "消息不存在"),
	PARAMS_ERROR(0x115, "参数错误"),
	UPLOAD_FAILED(0x116, "上传失败"),
	MSG_SAVE_FAILED(0x117, "重复保存"),
	MSG_PUBLISH_FAILED(0x118, "重复发布"),
	WECHAT_NOT_EXIST(0x119, "微信不存在"),
	PREVIEW_FAILED(0x120, "预览失败"),
	PUBLISH_FAILED(0x120, "发布失败"),
    ;
	private int code;
	private String msg;

	private BizErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static String getMsgByCode(int code) {
		BizErrorCode[] values = BizErrorCode.values();
		for (BizErrorCode bizErrorCode : values) {
			if (bizErrorCode.getCode() == code) {
				return bizErrorCode.getMsg();
			}
		}
		return null;
	}
}
