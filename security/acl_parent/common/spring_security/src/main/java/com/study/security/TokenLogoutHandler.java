package com.golaxy.security;

import com.golaxy.utils.R;
import com.golaxy.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lihang
 * @Date: 2021-06-24 22:09
 * @Description:
 */
public class TokenLogoutHandler implements LogoutHandler {

    private RedisTemplate redisTemplate;
    private TokenManager tokenManager;

    public TokenLogoutHandler(RedisTemplate redisTemplate, TokenManager tokenManager){
        this.redisTemplate = redisTemplate;
        this.tokenManager = tokenManager;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token");
        // 判断token是否无空，不为空则删除token
        if (StringUtils.isEmpty(token)){
            tokenManager.removeToken(token);

            String username = tokenManager.getUserInfoByToken(token);
            redisTemplate.delete(username);
        }
        ResponseUtil.out(httpServletResponse, R.ok());
    }
}
