package edu.hfut.innovate.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
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

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author : Chowhound
 * @since : 2023/7/24 - 16:10
 */
@SuppressWarnings("unused")
@Component
public class TokenManager {
    // token过期时间,默认为7天
    private static final Long tokenExpireSecond = 60 * 60 * 24 * 7L;
//    private static final Long tokenExpireSecond = 60 * 60 * 24 * 7 * 100L;
    public static final String HEADER_PREFIX = "Bearer ";

    public static final String ACCESS_TOKEN = "access_token";
    // 2h
    public static final Long ACCESS_TOKEN_EXPIRE = 60 * 60 * 2L;
    public static final String REFRESH_TOKEN = "refresh_token";
    // 8h
    public static final Long REFRESH_TOKEN_EXPIRE = 60 * 60 * 8L;

    @Autowired
    private AuthService authService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    // token加密密钥
    @Value("${custom.token.sign-key}")
    private String tokenSignKey;

    public String refreshAccessToken(UserVo userVo, String refreshToken) {
        String realRefreshToken = getRealToken(refreshToken);
        UserAuth userAuth = getUserAuth(realRefreshToken, REFRESH_TOKEN);
        if (userVo == null || userAuth == null || !Objects.equals(userAuth.getUserId(), userVo.getUserId())){
            return null;
        }

        return createToken(userVo, ACCESS_TOKEN_EXPIRE, ACCESS_TOKEN);
    }

    public Map<String, String> createDoubleToken(UserVo userVo) {
        Map<String, String> map = new HashMap<>();
        map.put(ACCESS_TOKEN, createToken(userVo, ACCESS_TOKEN_EXPIRE, ACCESS_TOKEN));
        map.put(REFRESH_TOKEN, createToken(userVo, REFRESH_TOKEN_EXPIRE, REFRESH_TOKEN));

        return map;
    }
    public UserAuth getUserAuth(String token) {
        return getUserAuth(token, ACCESS_TOKEN);
    }

    public UserAuth getUserAuth(String token, String prefix) {
        if (token == null || prefix == null) {
            return null;
        }
        String realToken = getRealToken(token);
        Long userId = getUserIdFromToken(realToken);
        UserAuth userAuth = getUserAuthFromRedis(realToken, prefix);

        if (userId == null || userAuth == null || !Objects.equals(userId, userAuth.getUserId())){
            return  null;
        }

        return userAuth;
    }

    private String createToken(UserVo userVo, Long expireTime, String prefix) {
        if (expireTime == null) expireTime = REFRESH_TOKEN_EXPIRE;
        // 生成token
        String token = Jwts.builder().setSubject(userVo.getUserId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();

        // TODO 权限设置
        List<String> authStrList = authService.listByUserId(userVo.getUserId()).stream()
                .map(Auth::toString).toList();

        UserAuth userAuth = BeanUtil.copyProperties(userVo, new UserAuth());
        userAuth.setRoles(authStrList);

        // 将token存入redis中
        redisTemplate.opsForValue().set(getRedisKey(token, prefix), JacksonUtil.toJsonString(userAuth),
                expireTime + RandomUtil.randomLong(0, 60L), TimeUnit.SECONDS);

        return token;
    }





    private UserAuth getUserAuthFromRedis(String token, String prefix) {
        String info = redisTemplate.opsForValue().get(getRedisKey(token, prefix));

        return info == null ? null : JacksonUtil.readValue(info, UserAuth.class);
    }
    private Long getUserIdFromToken(String token) {
        String info = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        if (StrUtil.isNumeric(info)) {
            return Long.valueOf(info);
        }
        return null;
    }

    public static String getRealToken(String token) {
        if (token != null && token.startsWith(HEADER_PREFIX)){
            return token.substring(HEADER_PREFIX.length());
        }
        return token;
    }

    private static String getRedisKey(String token, String prefix) {
        return prefix + "-" + token;
    }



    // region Deprecated

    @Deprecated
    public String createTokenWithExpireTime(UserVo userVo, Long expireTime) {

        JwtBuilder jwtBuilder = Jwts.builder().setSubject(userVo.getNickName());
        if (expireTime != null){
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expireTime));

        }
        String token = jwtBuilder
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();
        List<String> authStrList = authService.listByUserId(userVo.getUserId()).stream()
                .map(Auth::toString).toList();

        UserAuth userAuth = BeanUtil.copyProperties(userVo, new UserAuth());
        userAuth.setRoles(authStrList);

        // 将token存入redis中
        redisTemplate.opsForValue().set(token, JacksonUtil.toJsonString(userAuth),
                tokenExpireSecond * 2, TimeUnit.SECONDS);

        return token;
    }
    @Deprecated
    public String createToken(UserVo userVo) {
        return createTokenWithExpireTime(userVo, tokenExpireSecond * 1000);
    }
    @Deprecated
    public String createTokenNoLimit(UserVo userVo) {
        return createTokenWithExpireTime(userVo, null);
    }
    @Deprecated
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
    @Deprecated
    public void deleteTokenWithBearer(String tokenWithBearer) {
        redisTemplate.delete(resolveToken(tokenWithBearer));
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
    // TODO
//    public UserAuth getUserFromToken(String token) {
//        return JacksonUtil.readValue(
//                redisTemplate.opsForValue().get(token), UserAuth.class);
//    }
    // TODO 先不从redis拿
    public UserAuth getUserFromToken(String token) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1001L);
        return userAuth;
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

    public UserAuth getUserVoFromToken(String token) {
        return JacksonUtil.readValue(
                redisTemplate.opsForValue().get(token), UserAuth.class);
    }
    public UserAuth getUserVoFromTokenWithBearer(String tokenWithBearer) {
        return getUserVoFromToken(resolveToken(tokenWithBearer));
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
    // endregion
}
