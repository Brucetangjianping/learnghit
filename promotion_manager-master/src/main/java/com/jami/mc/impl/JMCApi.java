/**
 * @file JMCApi.java
 * 
 * @author feiyunpeng@tencent.com
 * @version 0.1
 * @date 2010-10-20
 * @bug
 * @warning
 */

package com.jami.mc.impl;

import com.jami.exception.BusinessException;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class JMCApi {
	
	private final long MAX_UINT_VALUE = (long)(Math.pow(2, 32) - 1);
	private final int MAX_USHORT_VALUE = (int)(Math.pow(2, 16) - 1);
	
	public interface JNAApi extends Library {

        String soPath = System.getenv("LIB_MC_PATH");
		
		public JNAApi INSTANCE = (JNAApi) Native.loadLibrary(soPath, JNAApi.class);
		
		public int Init(long key);
		public int AddResult(String cDesc, String pIP, int pPort, String method, long costTime, boolean success);
	}
	
	public void init(long key) throws BusinessException {
		
		if (key > MAX_UINT_VALUE) {
			throw new BusinessException(-1, "key is too large");
		}

		int rel = JNAApi.INSTANCE.Init(key);
		if (0 != rel) {
			throw new BusinessException(rel, "init error");
		}
	}
	
	public void addResult(String cDesc, String pIP, int pPort, String method, long costTime, boolean success) throws BusinessException {
		
		if (pPort > MAX_USHORT_VALUE) {
			throw new BusinessException(-1, "pPort is too large");
		}
		
		if (costTime > MAX_UINT_VALUE) {
			throw new BusinessException(-1, "costTime is too large");
		}
		
		if (null == cDesc || null == pIP || null == method) {
			throw new BusinessException(-1, "cDesc or pIp or method is null");
		}

		int rel = JNAApi.INSTANCE.AddResult(cDesc, pIP, pPort, method, costTime, success);
		if (0 != rel) {
			throw new BusinessException(rel, "addresult error");
		}
	}
	
	public static void main(String[] args) {
		
		JMCApi jmcApi = new JMCApi();
		
		try {
			jmcApi.init(55555);
		}
		catch (BusinessException e) {
			System.out.println(e.getErrMsg());
			System.out.println(e.getErrCode());
			return;
		}
		
		try {
			
			for (int i = 0; i < 100000; ++i) {
				jmcApi.addResult("tester", "123.123.123.123", 2000, "ok.ok", 10, true);
			}
		}
		catch (BusinessException e) {
			System.out.println(e.getErrMsg());
			System.out.println(e.getErrCode());
		}
		
	}
}

