package com.bs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.entity.*;
import com.bs.mapper.UserMapper;
import com.bs.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public User getUserById(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        return baseMapper.deleteById(id)==1?true:false;
    }

    @Override
    public User getUserInfo(String name) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", name));
        List<String> roleIds = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", user.getId()))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        if (roleIds != null && roleIds.size()>0) {
            Collection<Role> roles = roleService.listByIds(roleIds);
            user.setRoles(roles);

            List<String> menuIds = roleMenuService.selectRoleMenuByRoleIds(roleIds);
            if (menuIds != null &&menuIds.size()>0) {
                Collection<Menu> menus = menuService.listByIds(menuIds);
                user.setMenus(menus);
            }
        }
        return user;
    }
}
