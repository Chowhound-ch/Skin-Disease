package edu.hfut.innovate.common.util;

import cn.hutool.http.HttpUtil;
import edu.hfut.innovate.common.jackson.JacksonUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/8/3 - 21:42
 */
public class MiniProgramUtil {

    @Value("${wechat.appid}")
    public String APP_ID;
    @Value("${wechat.secret}")
    public String SECRET;

    public static final String GANT_TYPE = "authorization_code";
    public static final String WEI_XIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    public static final String OPEN_ID = "openid";

    public String getOpenId(String jsCode) {
        Map<String , String> map;
        try {
            map = JacksonUtil.readValue(
                    HttpUtil.get(WEI_XIN_URL, getParam(jsCode)), JacksonUtil.getMapOf(String.class, String.class));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map.get(OPEN_ID);
    }
    private Map<String, Object> getParam(String jsCode) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("appid", APP_ID);
        param.put("secret", SECRET);
        param.put("js_code", jsCode);
        param.put("grant_type", GANT_TYPE);
        return param;
    }
}
