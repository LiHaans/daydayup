package com.bs.service.impl;

import com.bs.entity.User;
import com.bs.security.entity.SecurityUser;
import com.bs.service.MenuService;
import com.bs.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: lihang
 * @Date: 2021-07-02 17:12
 * @Description:
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) throw new UsernameNotFoundException("用户名不能为空");

        User user = userService.selectByUsername(username);
        if (user == null) throw new UsernameNotFoundException("用户不存在");

        com.bs.security.entity.User curUser = new com.bs.security.entity.User();
        BeanUtils.copyProperties(user,curUser);

        //根据用户查询用户权限列表
        List<String> permissionValueList = menuService.selectMenuValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setMenuList(permissionValueList);


        return securityUser;
    }
}
