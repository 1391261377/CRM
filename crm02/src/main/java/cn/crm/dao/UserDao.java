package cn.crm.dao;

import cn.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: cn.crm
 * @description
 * @author: aoligei
 * @create: 2021-01-26 17:06
 **/
@Repository
public interface UserDao {

   User AllLogin(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    List<User> gitUserList();

//   User loginServlet(User user);

//   User login(User user);
}
