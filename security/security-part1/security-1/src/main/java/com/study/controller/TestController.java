package com.golaxy.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lihang
 * @Date: 2021-06-22 21:45
 * @Description:
 */
@Controller
public class TestController {

    @GetMapping("/test1")
    @ResponseBody
    @Secured({"ROLE_管理员"})
    public String test1(){
        return "Hello Security !";
    }

    @GetMapping("/test2")
    @ResponseBody
    @Secured({"ROLE_普通用户"})
    public String test2(){
        return "Hello Security !";
    }

    @GetMapping("index")
    public String index(){
        return "login";
    }

    @PostMapping("success")
    public String success(){
        return "success";
    }

    /*@PostMapping("error")
    public String error(){
        return "error";
    }*/

    @GetMapping("unauth")
    public String unauth(){
        return "unauth";
    }
}
