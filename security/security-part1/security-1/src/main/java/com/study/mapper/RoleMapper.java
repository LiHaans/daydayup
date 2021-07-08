package com.study.mapper;

import com.study.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author creator
 * @since 2021-06-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @description: 根据用户id查询角色
     * @auther: lihang
     * @date: 2021-06-23 21:06
     */
    List<Role> selectRolesByUserId(@Param("userId") Long userId);
}
