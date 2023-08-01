package edu.hfut.innovate.community.util;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author : Chowhound
 * @since : 2023/8/1 - 18:01
 */
public class ForwardUtil {

    private static final String FORWARD_PREFIX = "forward:";

    public static String generateForwardKey(Long userId, Long topicId){
        // md5 加密
        return DigestUtil.md5Hex(userId + topicId + FORWARD_PREFIX);
    }

}
