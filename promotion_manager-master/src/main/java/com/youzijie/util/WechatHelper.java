package com.youzijie.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jami.util.Log;
import com.mysql.jdbc.StringUtils;
import com.youzijie.appmsg.dao.WechatArticleDo;
import com.youzijie.appmsg.po.WechatArticlePo;
import com.youzijie.appmsg.po.WechatMsgPublishPo;
import com.youzijie.mp.dao.WechatDo;
import com.youzijie.util.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lei on 2015/7/20.
 */
public class WechatHelper {
    private static final String FILE_PATH = "/data/jweb_static/jweb_promotion_manager/upload/";
    private String wechatId;
    private String accessToken;
    private String appId;
    private String appSecret;
    private String mediaId;
    private Map<String, String> templates;

    public WechatHelper(WechatDo wechatDo, Map<String, String> templates) {
        this.wechatId = wechatDo.getWechatId();
        this.accessToken = wechatDo.getAccessToken();
        this.appId = wechatDo.getAppId();
        this.appSecret = wechatDo.getAppSecret();
        if (!updateAccessToken()) {
            this.accessToken = "";
        }
        this.templates = templates;
        this.mediaId = null;
    }

    private String handleResponse(String resp, String key) {
        JSONObject obj = JSONObject.parseObject(resp);
        if (resp.isEmpty() || obj == null) {
            Log.run.error("Get unrecognize response " + resp);
            return null;
        }
        Integer errcode = obj.getInteger("errcode");
        if (errcode == null || errcode == 0) {
            return obj.containsKey(key) ? obj.getString(key): "";
        } else {
            Log.run.error("Get response from wechat error, errcode: " + obj.getInteger("errcode") + " err:" + obj.getString("errmsg"));
            return null;
        }
    }

    private boolean publishMsgInternal(List<WechatArticleDo> wechatArticleDos, String reuqestTemplate, String urlTemplate, Map<String, String> request_remplates) {
        Integer msgId = 0;
        if (wechatArticleDos.isEmpty()) {
            Log.run.error("Publish message with no article");
            return false;
        } else {
            msgId = wechatArticleDos.get(0).getMsgId();
            Log.run.info("Try to publish message " + msgId  + " to wechat " + this.wechatId);
        }

        if (!uploadMsg(wechatArticleDos)) {
            Log.run.error("Upload message " + msgId + " failed");
            return false;
        }

        if (request_remplates == null) {
            request_remplates = new HashMap<String, String>();
        }
        request_remplates.put("mediaId", this.mediaId);
        request_remplates.put("accessToken", this.accessToken);
        String request = StringFormatter.format(reuqestTemplate,request_remplates, true);
        String url = StringFormatter.format(urlTemplate, request_remplates);
        try {
            Log.run.info("Send message " + msgId +" to " + this.wechatId + " request " + request);
            String resp = HttpRequest.doPost(url, request);
            String msg_id = handleResponse(resp, "msg_id");
            if (msg_id != null) {
                Log.run.info("Send message " + msgId + " success, msg:" + msg_id);
                return true;
            }
            Log.run.error("Upload message " + msgId + " failed, can not get msg_id from " + resp);
            return false;
        } catch (Exception e) {
            Log.run.error("Upload message " + msgId + " failed", e);
            return false;
        }
    }

    public boolean previewMsg(List<WechatArticleDo> wechatArticleDos, String previewWechatId$) {
        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token={accessToken}";
        String requestTemplate = "{" +
                "   \"towxname\":\"$previewWechatId$\", " +
                "   \"mpnews\":{              " +
                "            \"media_id\":\"$mediaId$\" " +
                "             }," +
                "   \"msgtype\":\"mpnews\" " +
                "}";
        Map<String, String> request_remplates = new HashMap<String, String>();
        request_remplates.put("previewWechatId", previewWechatId$);
        return publishMsgInternal(wechatArticleDos, requestTemplate, urlTemplate, request_remplates);
    }

    public boolean publishMsg(List<WechatArticleDo> wechatArticleDos) {
        String requestTemplate = "{" +
                "   \"filter\":{" +
                "      \"is_to_all\":true" +
                "   }," +
                "   \"mpnews\":{" +
                "      \"media_id\":\"$mediaId$\"" +
                "   }," +
                "    \"msgtype\":\"mpnews\"" +
                "}";
        String urlTemplate = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token={accessToken}";
        return publishMsgInternal(wechatArticleDos, requestTemplate, urlTemplate, null);
    }

    private boolean uploadMsg(List<WechatArticleDo> wechatArticleDos) {
        if (this.accessToken.isEmpty()) {
            if (!updateAccessToken()) {
                Log.run.error("Can not get access token");
                return false;
            }
        }

        if (wechatArticleDos.isEmpty()) {
            Log.run.error("Try to upload message with empty articles");
            return false;
        }

        List<WechatArticlePo> wechatArticlePos = new ArrayList<WechatArticlePo>();

        for (WechatArticleDo wechatArticleDo : wechatArticleDos) {
            WechatArticlePo wechatArticlePo = new WechatArticlePo(wechatArticleDo, templates);

            if (wechatArticleDo.getCover() != null && !wechatArticleDo.getCover().isEmpty()) {
                Log.run.info("Upload media in article " + wechatArticleDo.getId() + " to " + this.wechatId);
                String thumb_id = uploadMedia("image", wechatArticleDo.getCover());
                if (thumb_id.isEmpty()) {
                    Log.run.info("Upload cover of " + wechatArticleDo.getId() + " error");
                    continue;
                }
                Log.run.info("Upload cover of article " + wechatArticleDo.getId() + " return " + thumb_id);
                wechatArticlePo.setThumb_media_id(thumb_id);
            } else {
                Log.run.error("Upload media without cover in " + wechatArticleDo.getId() + " to " + this.wechatId);
                continue;
            }
            wechatArticlePos.add(wechatArticlePo);
        }
        Log.run.info("Uploaded " + wechatArticlePos.size() + " articles.");
        WechatMsgPublishPo wechatMsgPublishPo = new WechatMsgPublishPo();
        wechatMsgPublishPo.setArticles(wechatArticlePos);

        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + this.accessToken;
        try {

            String request =  JSON.toJSONString(wechatMsgPublishPo);
            Log.run.info("Send message " + request + " to " + this.wechatId);
            String resp = HttpRequest.doPost(url, request);
            this.mediaId = handleResponse(resp, "media_id");
            if (this.mediaId != null) {
                Log.run.info("Upload message success, media:" + this.mediaId);
                return true;
            }
            Log.run.error("Upload message failed, get resp " + resp);
            return false;
        } catch (Exception e) {
            Log.run.error("Upload message failed, get error ", e);
            return false;
        }
    }

    private boolean updateAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appId +
                "&secret=" + this.appSecret;
        Log.run.info("Try to update");
        try {
            String resp = HttpRequest.doGet(url);
            Log.run.info("Get resp " + resp);
            this.accessToken = handleResponse(resp, "access_token");
            if (this.accessToken != null) {
                Log.run.info("Update access token for wechat " + wechatId + " token:" + this.accessToken);
                return true;
            }
            Log.run.error("Can not get access token " + this.accessToken + " from " + resp);
            return false;
        } catch (Exception e) {
            Log.run.info("Get error" + e.getMessage(), e);
            return false;
        }
    }

    private String uploadMedia(String type, String filename) {
        //String type = "image";  // voice, video, thumb
        Log.run.info("upload " + type + " " + filename);
        String urlpath="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=" + this.accessToken + "&type=" + type;
        filename = filename.substring(filename.lastIndexOf("/"));
        filename = FILE_PATH + filename;

        try {
            String resp = HttpRequest.upload(urlpath, filename);
            String media_id = handleResponse(resp, "media_id");
            if (media_id != null) {
                Log.run.info("Upload media " + filename + " success, media:" + media_id);
            }
            return media_id;
        } catch (Exception e) {
            Log.run.error("Upload " + filename + "error", e);
            return "";
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
