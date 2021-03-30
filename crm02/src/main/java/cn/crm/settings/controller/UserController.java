package cn.crm.settings.controller;


import cn.crm.settings.domain.User;
import cn.crm.settings.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/selectLogin")
    public User loginAction(HttpSession session, String loginAct, String loginPwd)
    {

        User user =  userService.login(loginAct,loginPwd);
        System.out.println("name:"+loginAct+",password:"+loginPwd);
        if(user!=null)
        {
            session.setAttribute("user",user);
        }
        return user;

    }





}

