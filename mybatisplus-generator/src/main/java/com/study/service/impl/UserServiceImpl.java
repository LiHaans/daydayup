package com.golaxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.golaxy.entity.User;
import com.golaxy.mapper.UserMapper;
import com.golaxy.response.PageList;
import com.golaxy.response.WebResult;
import com.golaxy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-06-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageList<User> queryPage(int page, int rows) {
        Page<User> p = new Page(page, rows);
        IPage<User> iPage = userMapper.selectPage(p, null);
        return new PageList(iPage.getTotal(),iPage.getRecords());
    }

    @Override
    public WebResult<Boolean> addUser(User user) {
        int insert = userMapper.insert(user);
        return insert==1?WebResult.success("添加成功"):WebResult.error("添加失败");
    }

    @Override
    public WebResult<Boolean> deleteUser(Long id) {
        return userMapper.deleteById(id)==1?WebResult.success("删除成功"):WebResult.error("删除失败");
    }

    @Override
    public WebResult<Boolean> updateUser(User user) {

        return userMapper.updateById(user)==1?WebResult.success("更新成功"):WebResult.error("更新失败");
    }
}
