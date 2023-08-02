package edu.hfut.innovate.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import edu.hfut.innovate.common.domain.entity.Auth;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.community.UserVo;
import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.service.AuthService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author : Chowhound
 * @since : 2023/7/24 - 16:10
 */
@SuppressWarnings("unused")
@Component
public class TokenManager {
    // token过期时间,默认为7天
    private static final long tokenExpireTime = 60 * 60 * 24 * 7;
    public static final String HEADER_PREFIX = "Bearer ";
    @Autowired
    private AuthService authService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    // token加密密钥
    @Value("${custom.token.sign-key}")
    private String tokenSignKey;

    public String createToken(UserVo userVo) {

        String token = Jwts.builder().setSubject(userVo.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
        List<String> authStrList = authService.listByUserId(userVo.getUserId()).stream()
                .map(Auth::toString).toList();

        UserAuth userAuth = BeanUtil.copyProperties(userVo, new UserAuth());
        userAuth.setRoles(authStrList);
        redisTemplate.opsForValue().set(
                token, JacksonUtil.toJsonString(userAuth), tokenExpireTime, TimeUnit.SECONDS);

        return token;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }
    /**
     * 从token中获取用户名, token格式为Bearer token
     *
     * @author : Chowhound
     * @since : 2023/08/02 - 21:50
     */
    public String getUsernameFromTokenWithBearer(String tokenWithBearer) {
        return getUsernameFromToken(resolveToken(tokenWithBearer));
    }
    public UserAuth getUserFromToken(String token) {
        String userVoJson = redisTemplate.opsForValue().get(token);
        return JacksonUtil.readValue(userVoJson, UserAuth.class);
    }
    /**
     * 从token中获取用户信息, token格式为Bearer token
     *
     * @author : Chowhound
     * @since : 2023/08/02 - 21:50
     */
    public UserAuth getUserFromTokenWithBearer(String tokenWithBearer) {
        return getUserFromToken(resolveToken(tokenWithBearer));
    }

    public String resolveToken(String tokenWithBearer) {
        if (!StrUtil.isBlank(tokenWithBearer) && tokenWithBearer.startsWith(HEADER_PREFIX)) {
            return tokenWithBearer.substring(HEADER_PREFIX.length());
        }
        return null;
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

        return expiration == null || expiration.after(DateUtil.date());
    }



    public static void main(String[] args) {
        String token = Jwts.builder().setSubject("root")
                .signWith(SignatureAlgorithm.HS512, "B*1$w9^OrEn%nIRcT_ke7if5R:-*EMnOW")
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
        System.out.println(token);
    }

}
