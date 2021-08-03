package com.bs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.entity.RoleMenu;
import com.bs.mapper.RoleMenuMapper;
import com.bs.service.RoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public List<String> selectRoleMenuByRoleIds(List<String> roleIds) {
        return baseMapper.selectRoleMenuByRoleIds(roleIds);

    }
}
