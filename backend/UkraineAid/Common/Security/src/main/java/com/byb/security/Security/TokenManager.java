package com.byb.security.Security;

import com.alibaba.fastjson.JSONObject;
import com.byb.security.Entity.SecurityUser;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {

    private Long tokenLife = Long.valueOf(2 * 60 * 60 * 1000);
    public static final String tokenSignKey = "syhxsqq";

    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenLife))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public String getUserInfoFromToken(String token) {
        try {
            String userinfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
            return userinfo;
        }catch (SignatureException e){
            throw e;
        }
    }

    private static Claims getTokenBody(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException e){
            e.printStackTrace();
        } catch(UnsupportedJwtException e){
            e.printStackTrace();
        } catch(MalformedJwtException e){
            e.printStackTrace();
        } catch(SignatureException e){
            e.printStackTrace();
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return claims;
    }

    public void removeToken(String token) {

    }

}
