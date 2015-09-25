package com.jami.mc.impl;


import com.jami.exception.BusinessException;
import com.jami.mc.MCMonitor;
import com.jami.mc.McUtil;
import com.jami.mc.Module;
import com.jami.util.EnvManager;
import com.jami.util.Log;
import org.springframework.util.StringUtils;

/**
 * 
 * @author:jiaqiangxu
 * @date: 2012-5-9
 * @desc:封装移动电商的监控上报，统一使用该类获取上报代理的单个实例
 * @version: 1.0
 */
public class JMCMonitor implements MCMonitor {

	/**
	 * 上报用的jmc api
	 */
	private static JMCMonitor jMCMonitorInstance=new JMCMonitor();
	private static JMCApi jMCApi = null;

	static {
		try {
			jMCApi = new JMCApi();
			if (null != jMCApi) {
				jMCApi.init(1234);
			}
		} catch (BusinessException e) {
			Log.run.warn("jMCApi init error!", e);
		}catch (Throwable e) {
			Log.run.error("jMCApi init Throwable error!", e);
		}
	}

	/**
	 * 防止外部对其实例化，保证只有一个实例
	 */
	private JMCMonitor() {

	}

	public static JMCMonitor getInstance() {
		return jMCMonitorInstance;
	}
	
	public static void mossReport(Module module, String interfaceName, long costTime, boolean isNeedAlarm) {
		JMCMonitor.getInstance().addResult(module.getName(), McUtil.DEFAULT_IP,  0,
	      		McUtil.gernateMethodName(module.getName(), interfaceName),  costTime, !isNeedAlarm);
    }
	
	public static void mossReport(Module module,String interfaceName,long costTime, boolean isNeedAlarm, String ip, int port) {
		JMCMonitor.getInstance().addResult(module.getName(), ip, port,
	       		McUtil.gernateMethodName(module.getName(), interfaceName), costTime, !isNeedAlarm);
	}
	
	public static void mossServerReport(Module module, String interfaceName, long costTime, boolean isNeedAlarm) {
		JMCMonitor.getInstance().addResult(module.getDesc(), McUtil.getLocalRealIp(), McUtil.DEFAULT_PORT,
				McUtil.gernateMethodName(module.getName(), interfaceName), costTime, !isNeedAlarm);
	}
  
	public  void addResult(String callDesc,String ip, int pPort,
			String method, long costTime, int ret, long result) {
		//long start=System.currentTimeMillis();	
		boolean success = ret == 0 && result == 0;

		try {
				callDesc= StringUtils.isEmpty(callDesc)?"qqbuy":callDesc;
				ip= StringUtils.isEmpty(ip)? McUtil.DEFAULT_IP:ip;
				pPort=pPort==0? McUtil.DEFAULT_PORT:pPort;
			jMCApi.addResult(callDesc, ip, pPort, method,
					costTime, success);

		} catch (Exception e) {
			Log.run.warn("JMCApi addResult error!callDesc["+callDesc+
					"],ip["+ip+"],pPort["+pPort+"],method["+method
					+"],costTime["+costTime+"],ret["
					+ret+"],result["+result+"]", e);
		}catch (Throwable e) {
//			Log.run.error("JMCApi addResult error!callDesc["+callDesc+
//					"],ip["+ip+"],pPort["+pPort+"],method["+method
//					+"],costTime["+costTime+"],ret["
//					+ret+"],result["+result+"]", e);		
		}
//		finally{
//			Log.run.debug("JMCApi addResult!callDesc["+callDesc+
//					"],ip["+ip+"],pPort["+pPort+"],method["+method
//					+"],costTime["+costTime+"],ret["
//					+ret+"],result["+result+"] cost "+(System.currentTimeMillis()-start)+"ms");
//		}
	}
	public  void addResult(String callDesc,String ip, int pPort,
			String method, long costTime, boolean isSuc) {
		//long start=System.currentTimeMillis();	
		try {
			callDesc= StringUtils.isEmpty(callDesc)?"qqbuy":callDesc;
			ip= StringUtils.isEmpty(ip)? McUtil.DEFAULT_IP:ip;
			pPort=pPort==0? McUtil.DEFAULT_PORT:pPort;
			jMCApi.addResult(callDesc, ip, pPort, method,
					costTime, isSuc);
		} catch (Exception e) {
			if(EnvManager.isIDCEnvCurrent()){
				Log.run.warn("JMCApi addResult error!callDesc["+callDesc+
						"],ip["+ip+"],pPort["+pPort+"],method["+method
						+"],costTime["+costTime+"],result["+isSuc+"]", e);
			}
		
		}catch (Throwable e) {
//			Log.run.error("JMCApi addResult error!callDesc["+callDesc+
//					"],ip["+ip+"],pPort["+pPort+"],method["+method
//					+"],costTime["+costTime+"],result["+isSuc+"]", e);		
		}
//			finally{
//			Log.run.debug("JMCApi addResult!callDesc["+callDesc+
//					"],ip["+ip+"],pPort["+pPort+"],method["+method
//					+"],costTime["+costTime+"],result["+isSuc+"] cost "+(System.currentTimeMillis()-start)+"ms");
//			
//		}
	}
	
	 public static void main(String args[]){
		try {
			JMCApi jMCApi = new JMCApi();
			if (null != jMCApi) {
				jMCApi.init(0);
			}
		}	
			
		catch (Exception e) {
			System.out.println("INIT ERROR"+e);
		}
		catch (Throwable e) {
			System.out.println("INIT Throwable ERROR"+e);
		}
	}

}
