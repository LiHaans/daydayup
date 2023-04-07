package com.golaxy.service.impl;

import com.golaxy.entity.SecurityUser;
import com.golaxy.entity.User;
import com.golaxy.service.PermissionService;
import com.golaxy.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) throw new UsernameNotFoundException("用户名不能为空");

        User user = userService.selectByUsername(username);
        if (user == null) throw new UsernameNotFoundException("用户不存在");

        com.golaxy.entity.User curUser = new com.golaxy.entity.User();
        BeanUtils.copyProperties(user,curUser);

        //根据用户查询用户权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);


        return securityUser;
    }
}
