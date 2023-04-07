package com.golaxy.service.impl;

import com.golaxy.entity.Users;
import com.golaxy.mapper.UsersMapper;
import com.golaxy.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-06-22
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
