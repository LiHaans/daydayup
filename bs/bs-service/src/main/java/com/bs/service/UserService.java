package com.bs.service;

import com.bs.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author creator
 * @since 2021-07-28
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);

    User getUserById(Integer id);

    boolean deleteUserById(Integer id);

    User getUserInfo(String name);
}
