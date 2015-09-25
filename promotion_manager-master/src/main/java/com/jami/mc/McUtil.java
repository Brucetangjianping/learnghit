package com.jami.mc;


import com.jami.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 
 * @author:jiaqiangxu
 * @date: 2012-5-15
 * @desc:mc的上报工具类
 * @version: 1.0
 */
public class McUtil {
	
	/**
	 * #模调监控组名称，目前有6种，qgo：手拍，mwg：网购，weigou：微购，welive：微生活，lottery：彩票，other：其他
	 * #如未配置此参数或者配置的信息在moss监控组中未查询到默认为order其他
	 * #模调调用方系统名称，将用于拼装到模块名称前，一般取服务名后半部分，或者项目的缩写 #请注意不要重名和规划好后不要随意修改
	 */
	public static String MC_GROUP_NAME = "mpos"; // 模调监控组名称,请从McConstrant中选取,如需要新增请在moss中先增加相应监控组
	public static String MC_CALLER_NAME = "mpos_api";// 访问系统名称

	public static int DEFAULT_PORT = 9020;
	public static String DEFAULT_IP = "0.0.0.0";
	public static String REQUEST_WAP_TYPE = "wap2";//wap2版
	public static String REQUEST_API_TYPE = "api";//api版
	public static String REQUEST_TOUCH_TYPE = "touch";//touch版
	

	public static void main(String[] args) throws SocketException {
		System.out.println(McUtil.getLocalRealIp());

	}

	public static String getLocalRealIp()  {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP

		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress()&& !ip.isLoopbackAddress()&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress()&& !ip.isLoopbackAddress()&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
		} catch (SocketException e) {
			Log.run.error("getRealIp error", e);
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}
	public static String gernateMethodName(String moduleName, String interfaceName) {
		return new StringBuffer(MC_GROUP_NAME).append("|").append(MC_CALLER_NAME).append("_").append(moduleName).append(".").append(interfaceName)
				.toString();
	}

	public static String gernateOpenAPIModuleName(String moduleName, String interfaceName) {
		return new StringBuffer(MC_GROUP_NAME).append("|").append(MC_CALLER_NAME).append("_").append("c_pp_openapi_").append(moduleName).append(".")
				.append(interfaceName).toString();
	}
	public  static boolean isNeedReport(int ret){
    	return ret==0;
    }

}
