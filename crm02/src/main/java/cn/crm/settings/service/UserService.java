package cn.crm.settings.service;


import cn.crm.settings.domain.User;


import java.util.List;

/**
 * @program: cn.crm
 * @description
 * @author: aoligei
 * @create: 2021-01-26 17:14
 **/

public interface UserService {
//    boolean login(String loginAct, String loginPwd);
    User login(String loginAct, String loginPwd);

    List<User> gitUserList();

}

