package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.study.entity.Menu;
import com.study.entity.Role;
import com.study.entity.Users;
import com.study.mapper.MenuMapper;
import com.study.mapper.RoleMapper;
import com.study.mapper.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Auther: lihang
 * @Date: 2021-06-22 22:10
 * @Description:
 */
@Service("userDetailService")
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UsersMapper usersMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Users user = usersMapper.selectOne(queryWrapper);

        if (user == null) throw new UsernameNotFoundException("用户不存在");

        // 处理用户的角色 权限
        List<GrantedAuthority> authorities = new ArrayList<>();

        List<Role> roles = roleMapper.selectRolesByUserId(user.getId());
        roles.forEach(r->{authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));});

        List<Menu> menus = menuMapper.selectMenuByUserId(user.getId());
        menus.forEach(m->{authorities.add(new SimpleGrantedAuthority(m.getPermission()));});


        log.info(user.toString());
        return new User(username,user.getPassword(), authorities);
    }
}
