package com.study.service.impl;

import com.study.entity.TUser;
import com.study.mapper.TUserMapper;
import com.study.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author creator
 * @since 2021-11-15
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
