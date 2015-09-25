package com.jami.mc;

/**
 * @version 1.2
 * @author:jiaqiangxu
 * @date: 2012-11-29
 * @desc:模块名，共分为4段,每段以"_"分隔, 以下为规范: 第1段:表示是在客户端/服务端监控(c/s),
 *                           第2段:表示监控的系统所属组织,(pp表示拍拍
 *                           ,wg表示网购,之前用mm表示移动电商服务会有冲突，此处需要细分为qgo
 *                           (手机拍拍)/mwg(手机网购)/itg(O2O))
 *                           第3段:表示监控的接口协议类型(idl,opeanapi,wap版,touch版等等)
 *                           第4段:表示监控的业务模块
 *                           ,如deal(订单)、user(用户)、item(商品),如果调用的是移动电商提供的服务
 *                           ，这个字段可以参考提供服务的appj名
 * 
 *                           1.在客户端监控，命名格式如下: C_PP_IDL_DEAL
 *                           (在客户端监控拍拍提供的IDL协议的deal服务) C_WG_OPENAPI_DEAL
 *                           (在客户端监控网购提供的openAPI形式的deal服务) C_QGO_IDL_DEAL
 *                           (在客户端监控手机拍拍提供的IDL形式的deal服务) C_MWG_IDL_DEAL
 *                           (在客户端监控手机网购提供的IDL形式的deal服务)
 * 
 * 
 *                           2.在服务端监控，命名格式如下： S_QGO_W_USER(在手拍wap2的服务端监控user请求)
 *                           S_MWG_T_DEAL(在手机网购的触屏版服务端监控deal请求)
 * @version: 1.0
 */
public enum Module
{
    // ***************Action接口的监控************************************
    S_MPOS_API_DEFAULT("s_mpos_default", "默认服务"),
    ;

    private String name;
    private String desc;

    private Module(String name, String desc)
    {
        this.desc = desc;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
