package com.jami.mc;
 /**
  * 
  * @author:jiaqiangxu
  * @date:  2012-5-9
  * @desc:监控移动电商的调用外部请求
  * @version: 1.0
  */
public interface MCMonitor {
	  
	/**
	 * 
	 * @author:jiaqiangxu
	 * @date:2012-5-9
	 * @desc:封装外部IDL调用的上报
	 * @param:cDesc,调用者描述
	 * @param:ip,被调方IP
	 * @param:pPort,被调方端口
	 * @param:pPort,被调方接口信息,使用.分割模块和方法名
	 * @param:costTime,调用耗时
	 * @param:ret,IDL调用客户端结果
     * @param:result,IDL调用服务端结果
	 * @return:
	 */
	  void addResult(String callDesc, String ip, int pPort,
                     String method, long costTime, int ret, long result);
	  
	  void addResult(String callDesc, String ip, int pPort,
                     String method, long costTime, boolean isSuc);
}

