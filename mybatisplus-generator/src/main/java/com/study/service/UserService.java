package com.study.service;

import com.study.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.response.PageList;
import com.study.response.WebResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author creator
 * @since 2021-06-16
 */
public interface UserService extends IService<User> {

    /**
     * @description: 分页查询
     * @auther: lihang
     * @date: 2021-06-16 16:42
     */
    PageList<User> queryPage(int page, int rows);

    /**
     * @description: 添加用户
     * @auther: lihang
     * @date: 2021-06-16 16:48
     */
    WebResult<Boolean> addUser(User user);

    /**
     * @description: 删除用户
     * @auther: lihang
     * @date: 2021-06-16 16:52
     */
    WebResult<Boolean> deleteUser(Long id);

    /**
     * @description: 更新用户
     * @auther: lihang
     * @date: 2021-06-16 16:55
     */
    WebResult<Boolean> updateUser(User user);
}
