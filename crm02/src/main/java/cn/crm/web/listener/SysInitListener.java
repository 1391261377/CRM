package cn.crm.web.listener;

import cn.crm.dao.DicTypeDao;
import cn.crm.dao.DicValueDao;
import cn.crm.settings.domain.DicValue;
import cn.crm.settings.service.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-03 14:18
 **/

@WebListener
public class SysInitListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("监听器开启");

        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        //获取bean

        DicTypeDao typeDao = context.getBean(DicTypeDao.class);
        DicValueDao valueDao = context.getBean(DicValueDao.class);
        DicService dicService = context.getBean(DicService.class);


        ServletContext application = event.getServletContext();

        Map<String, List<DicValue>> map = dicService.getAll();

        //将map解析为上下域对象中保存的键值对
        Set<String> set = map.keySet();
        for (String key : set
        ) {
            application.setAttribute(key, map.get(key));
            System.out.println(key);
        }

        Map<String,String> pMap=new HashMap<>();
        //解析Properties文件
        ResourceBundle rb =  ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e=rb.getKeys();
        while (e.hasMoreElements()){
            String key =e.nextElement();
            String value=rb.getString(key);
            pMap.put(key,value);
            application.setAttribute("pMap",pMap);
        }

    }


}
