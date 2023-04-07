package com.golaxy.security;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther: lihang
 * @Date: 2021-06-24 21:54
 * @Description:
 */
@Component
public class TokenManager {

    private long expireTime = 1000*60*60*24;

    private String tokenSignKey = "123456";

    // 生成token
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(expireTime+System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    // 根据token获取用户信息
    public String getUserInfoByToken(String token){
        String username = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return username;
    }

    // 删除token
    public void removeToken(String token){}
}
