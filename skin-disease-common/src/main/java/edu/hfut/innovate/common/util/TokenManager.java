package edu.hfut.innovate.common.util;

import cn.hutool.core.date.DateUtil;
import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.vo.community.UserVo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author : Chowhound
 * @since : 2023/7/24 - 16:10
 */
@Component
public class TokenManager {
    // token过期时间,默认为7天
    private static final long tokenExpireTime = 60 * 60 * 24 * 7;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    // token加密密钥
    private static final String tokenSignKey = "chowhound";

    public String createToken(UserVo userVo) {

        String token = Jwts.builder().setSubject(userVo.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();

        redisTemplate.opsForValue().set(token, JacksonUtil.toJsonString(userVo), tokenExpireTime, TimeUnit.SECONDS);

        return token;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    public UserVo getUserFromToken(String token) {
        String userVoJson = redisTemplate.opsForValue().get(token);
        return JacksonUtil.readValue(userVoJson, UserVo.class);
    }

    public Boolean isTokenExist(String token) {
        // 判断jwt的token是否过期
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }

        Date expiration = jws.getBody()
                .getExpiration();

        return expiration == null || expiration.before(DateUtil.date());
    }


//
//    public static void main(String[] args) {
//        String token = Jwts.builder().setSubject("chowhound")
//                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
//                .compressWith(CompressionCodecs.DEFLATE)
//                .compact();
//        System.out.println(token);
//    }

}
