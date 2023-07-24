package edu.hfut.innovate.common.util;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : Chowhound
 * @since : 2023/7/24 - 16:10
 */
@Component
public class TokenManager {
    // token过期时间,默认为7天
    private final long tokenExpireTime = 60 * 60 * 24 * 7;

    // token加密密钥
    private final String tokenSignKey = "chowhound";

    public String createToken(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }


}
