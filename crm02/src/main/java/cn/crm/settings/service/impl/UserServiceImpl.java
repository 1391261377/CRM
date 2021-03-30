package cn.crm.settings.service.impl;


import cn.crm.dao.UserDao;


import cn.crm.settings.domain.User;
import cn.crm.settings.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @program: cn.crm
 * @description
 * @author: aoligei
 * @create: 2021-01-26 17:14
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd) {
            User user=userDao.AllLogin(loginAct,loginPwd);
            if (user!=null){
                return user;
            }else
        return null;
    }



    @Override
    public List<User> gitUserList() {


        return userDao.gitUserList();
    }

}
