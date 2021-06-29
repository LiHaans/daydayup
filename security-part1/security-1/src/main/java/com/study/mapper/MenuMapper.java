package com.study.mapper;

import com.study.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * @description: 根据用户查询菜单
     * @auther: lihang
     * @date: 2021-06-23 21:01
     */
    List<Menu> selectMenuByUserId(@Param("userId") Long userId);
}
