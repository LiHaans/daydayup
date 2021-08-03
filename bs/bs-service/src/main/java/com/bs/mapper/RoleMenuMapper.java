package com.bs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<String> selectRoleMenuByRoleIds(@Param("list") List<String> roleIds);
}
