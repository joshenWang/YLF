package com.njupt.sso.controller;

import com.njupt.pojo.TbUser;
import com.njupt.pojo.YlfResult;
import com.njupt.sso.service.checkUserInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Title:
 * description:注册页面的controller
 * Create Time: 2017/12/23 17:35 星期六
 *
 * @author: WJZ
 **/
@Controller
public class RegisterController {

    @Resource
    private checkUserInterface userInterface;

    //登陆注册页面
    @RequestMapping("/page/register")
    public String showRegisterPage()
    {
        return "register";

    }

    //注册信息校验
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public YlfResult checkData(@PathVariable String param,@PathVariable Integer type)
    {

         YlfResult result = userInterface.findUserInfo(param, type);

         return result;

    }

    //用户注册
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public YlfResult register(TbUser user)
    {

        YlfResult result = userInterface.InsertUser(user);
        return result;

    }

    //跳转到登陆页面
    @RequestMapping("/page/login")
    public String pageLogin(String redirect,Model model)
    {

        model.addAttribute("redirect",redirect);
        return "login";

    }


}
