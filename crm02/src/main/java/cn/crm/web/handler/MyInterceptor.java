package cn.crm.web.handler;


import cn.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//拦截器类 拦截用户的请求
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");

        if (user != null) {
            return true;
        }else {

            System.out.println("登录拦截");

            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }

    }
}
