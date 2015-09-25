package com.jami.util;

import java.util.Properties;

/**
 * Created by felixzhao on 14-5-19.
 */
public class EnvManager {
    public static final String ENV_VARIALE_NAME = "cfg.env";
    public static final int ENV_TYPE_LOCAL = 1;
    public static final int ENV_TYPE_DEV = 2;
    public static final int ENV_TYPE_BETA = 3;
    public static final int ENV_TYPE_IDC_PREVIEW = 4;
    public static final int ENV_TYPE_IDC = 5;


    public static final String ENV_NAME_LOCAL = "local";
    public static final String ENV_NAME_DEV = "dev";
    public static final String ENV_NAME_BETA = "beta";
    public static final String ENV_NAME_IDC_PREVIEW = "idc_preview";
    public static final String ENV_NAME_IDC = "idc";

    public static final String IDL_CALL_ENV_NAME = "idl.cfg.env";
    public static final String IDL_CALL_ENV_DEV = "idl_dev";
    public static final String IDL_CALL_ENV_BETA = "idl_beta";
    public static final String IDL_CALL_ENV_IDC = "idl_idc";

    public static boolean isIdlProxyDevOrBeta() {
        return "172.25.32.43".equals(System.getProperty("AsynWeb.proxyIp"));
    }

    public static boolean isIdlProxyIdc() {
        return "172.23.0.58".equals(System.getProperty("AsynWeb.proxyIp"));
    }

    public static boolean isLocalEnvCurrent() {
        if (ENV_TYPE_LOCAL == getCurrentEnv()) {
            return true;
        }
        return false;
    }

    public static boolean isIDCEnvCurrent() {
        if (ENV_TYPE_IDC == getCurrentEnv()) {
            return true;
        }
        return false;
    }

    public static boolean isBETAEnvCurrent() {
        if (ENV_TYPE_BETA == getCurrentEnv()) {
            return true;
        }
        return false;
    }

    public static boolean isGAMMAEnvCurrent() {
        if (ENV_TYPE_IDC_PREVIEW == getCurrentEnv()) {
            return true;
        }
        return false;
    }

    /*************************************************** Helper Method ************************************************/
    protected static int getCurrentEnv() {
        Properties sysProp = System.getProperties();
        String env = sysProp.getProperty(ENV_VARIALE_NAME);
        if (ENV_NAME_DEV.equals(env)) {
            return ENV_TYPE_DEV;
        } else if (ENV_NAME_BETA.equals(env)) {
            return ENV_TYPE_BETA;
        } else if (ENV_NAME_IDC_PREVIEW.equals(env)) {
            return ENV_TYPE_IDC_PREVIEW;
        } else if (ENV_NAME_IDC.equals(env)) {
            return ENV_TYPE_IDC;
        } else if (ENV_NAME_LOCAL.equals(env)) {
            return ENV_TYPE_LOCAL;
        }
        return -1;
    }

    protected static String getIDLCurrentEnv() {
        Properties sysProp = System.getProperties();
        String env = sysProp.getProperty(IDL_CALL_ENV_NAME);
        return env;
    }

    public static String getCurrentEnvName() {
        Properties sysProp = System.getProperties();
        String env = sysProp.getProperty(ENV_VARIALE_NAME);
        return env == null ? "unknown" : env;
    }

}
