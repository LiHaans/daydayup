package com.bs.service;

import com.bs.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
public interface RoleMenuService extends IService<RoleMenu> {

    List<String> selectRoleMenuByRoleIds(List<String> roleIds);
}
