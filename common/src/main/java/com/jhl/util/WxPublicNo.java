package com.jhl.util;

/**
 * 微信公众号信息及接口调用地址
 */
public class WxPublicNo {
    /**
     * 测试账号信息
     */

    public final static String appId = "wxf4d33e0d6954b128";
    public final static String token = "jhlWechat";
    public final static String appSecret = "0f424e07d1693d74f5b088b59aa13e82";

    /**
     * 模板消息的模板ID
     */
    public final static String query_balance = "7UF1xFTQU3OLSqDKF61YKWspTaRJieg6zIEyrwJZ3Zo";//余额查询
    public final static String recharge_notice = "VIYDCH9U7MCVj_HFRAutePswItwRzd_QOPFsdN-PN1w";//充值通知
    public final static String recharge_success = "pFgtNodsp-Uys82jjYkfZLNqToPiHknxhnZAY33iWz8";//充值成功通知
    public final static String cash_advance_apply = "IocaJQq-XqHcebMU6K5JiHZWiWoNJ0oe-hVbcpCD-74";//提现申请通知
    public final static String cash_advance_success = "YOCd-0068M15E65PlP8fd0_ITmVk8X5rJEqUAOEabks";//提现成功通知
    public final static String cash_advance_fail = "v2BpKz3dT5Y0gwNnUebm0h8XrVm6rsjbRu6GcIJJl5w";//提现失败通知
    public final static String invest_apply = "18fX39W4uj8d4XnXUawpXdqGajweOPV9EeXeAiyHV4I";//投资申请通知
    public final static String invest_status = "";//投资状态更新





    /**
     * 接口调用地址信息
     */
    //获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //获取引导授权链接
    public final static String oauth2_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    //查询微信菜单链接
    public final static String menu_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    //发送模板消息地址
    public final static String send_psd_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //获取素材列表地址
    public final static String material_list_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";

    //获取永久素材地址
    public final static String get_material_url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
}
