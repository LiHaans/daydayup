package com.study.filter;

import com.study.security.TokenManager;
import com.study.utils.R;
import com.study.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Auther: lihang
 * @Date: 2021-06-24 22:31
 * @Description:  授权filter
 */
@Slf4j
public class TokenAuthFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("===="+request.getRequestURL());
        if (request.getRequestURI().indexOf("admin") == -1) {
            chain.doFilter(request, response);
        }

        UsernamePasswordAuthenticationToken authentication = null;
        authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            ResponseUtil.out(response, R.error());
        }
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        // token置于header里
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            String username = tokenManager.getUserInfoByToken(token);

            List<String> permissionValueList = (List<String>)redisTemplate.opsForValue().get(username);

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) continue;
                authorities.add(new SimpleGrantedAuthority(permissionValue));
            }


            if (!StringUtils.isEmpty(username)) {
                return new UsernamePasswordAuthenticationToken(username, token, authorities);
            }
            return null;

        }
        return null;
    }
}
