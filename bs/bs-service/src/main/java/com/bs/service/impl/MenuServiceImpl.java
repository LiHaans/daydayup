package com.bs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.entity.Menu;
import com.bs.mapper.MenuMapper;
import com.bs.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectMenuValueByUserId(Integer id) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<String> menus = baseMapper.selectList(wrapper).stream().map(Menu::getTitle).collect(Collectors.toList());
        return menus;
    }
}
