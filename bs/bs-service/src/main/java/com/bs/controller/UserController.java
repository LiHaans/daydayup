package com.bs.controller;


import com.bs.entity.User;
import com.bs.response.WebResult;
import com.bs.security.entity.SecurityUser;
import com.bs.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserDetailsService userDetailsService;


    @GetMapping("{id}")
    public WebResult<User> getUserById(@PathVariable("id")Integer id){
        if (id == null || id < 0 ) return WebResult.error("id有误");

        User user = userService.getUserById(id);
        return WebResult.success(user);
    }

    @DeleteMapping("{id}")
    public WebResult<User> deleteUserById(@PathVariable("id")Integer id) {
        if (id == null || id < 0 ) return WebResult.error("id有误");

        boolean result = userService.deleteUserById(id);
        return WebResult.success(result);
    }

    @GetMapping("getCurrentUser")
    public WebResult getCurrentUser(Principal principal){
        if (!StringUtils.isEmpty(principal.getName())) {
            User user = userService.getUserInfo(principal.getName());
            return WebResult.success(user);
        }
        return WebResult.error();
    }

}

