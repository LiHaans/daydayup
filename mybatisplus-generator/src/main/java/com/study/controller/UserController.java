package com.study.controller;


import com.study.entity.User;
import com.study.response.PageList;
import com.study.response.WebResult;
import com.study.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author creator
 * @since 2021-06-16
 */
@RestController
@RequestMapping("/study/user")
@Api(tags="用户操作")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("queryPage")
    @ApiOperation(value="分页查询")
    public WebResult<PageList<User>> queryPage(int page, int rows){
        PageList<User> pages = userService.queryPage(page, rows);
        return WebResult.success(pages);
    }


    @PutMapping("user")
    @ApiOperation(value="添加用户")
    public WebResult<Boolean> addUser(@RequestBody User user){
        WebResult<Boolean> result = userService.addUser(user);
        return result;
    }


    @DeleteMapping("user")
    @ApiOperation(value="删除用户")
    public WebResult<Boolean> deleteUser(@ApiParam(value="ID") @RequestParam(name="id")Long id){
        WebResult<Boolean> result = userService.deleteUser(id);
        return result;
    }

    @PostMapping("update")
    @ApiOperation(value="更新用户")
    public WebResult<Boolean> updateUser(@RequestBody User user){
        WebResult<Boolean> result = userService.updateUser(user);
        return result;
    }


}

