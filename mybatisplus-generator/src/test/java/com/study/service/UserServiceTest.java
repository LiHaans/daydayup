package com.golaxy.service;

import com.golaxy.entity.User;
import com.golaxy.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Auther: lihang
 * @Date: 2021-06-16 17:15
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserMapper userMapper;

    // 测试乐观锁成功！
    @Test
    public void testOptimisticLocker(){
        // 1、查询用户信息
        User user = userMapper.selectById(1405090389195812865L);
        // 2、修改用户信息
        user.setName("ceshi1");
        user.setEmail("1234567@qq.com");
        // 3、执行更新操作
        userMapper.updateById(user);
    }

    // 测试乐观锁失败！多线程下
    /*
     UPDATE user SET
        name='ceshi1',
        age=12,
        email='24736743@qq.com',
        version=3,
        create_time='2021-06-16 17:10:29',
        update_time='2021-06-16 17:34:55.614'
    WHERE
        id=1405090389195812865
        AND version=2
        AND deleted=0

        sql如上，多线程更新操作同一条数据时,第一个线程数据更新后version已发生改变,第二个线程已查不到数据
     **/
    @Test
    public void testOptimisticLocker2(){
        // 线程 1
        User user = userMapper.selectById(1405090389195812865L);
        user.setName("ceshi1");
        user.setEmail("24736743@qq.com");
        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1405090389195812865L);
        user2.setName("ceshi22");
        user2.setEmail("24736743@qq.com");
        userMapper.updateById(user2);
        // 自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }
}
