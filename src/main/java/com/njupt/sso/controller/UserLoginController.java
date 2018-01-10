package com.njupt.sso.controller;


import com.njupt.pojo.CookieUtils;
import com.njupt.pojo.YlfResult;
import com.njupt.sso.service.UserLoginInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title:
 * description:用户登陆的controller
 * Create Time: 2017/12/23 21:14 星期六
 *
 * @author: WJZ
 **/
@Controller
public class UserLoginController {

    @Resource
    private UserLoginInterface loginInterface;

    //用户登陆
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public YlfResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
    {
        YlfResult result = loginInterface.UserLogin(username, password);
        //要检查业务层的逻辑是否成功，成功了才能王cookie中写
        if (result.getStatus()==200)
        {
            String  token = (String)result.getData();
            CookieUtils.setCookie(request,response,"TT_TOKEN",token);
            return result;

        }

        return null;
    }

    //根据token获得用户信息
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable String token,String callback)
    {

        YlfResult result = loginInterface.GetUserInfoByToken(token);

        if (StringUtils.isNotBlank(callback))
        {
            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;

        }
        return result;


    }


}
