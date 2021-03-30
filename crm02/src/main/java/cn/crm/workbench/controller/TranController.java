package cn.crm.workbench.controller;

import cn.crm.settings.domain.User;
import cn.crm.settings.service.UserService;
import cn.crm.utils.DateTimeUtil;
import cn.crm.utils.UUIDUtil;
import cn.crm.workbench.domain.Tran;
import cn.crm.workbench.domain.TranHistory;
import cn.crm.workbench.service.CustomerService;
import cn.crm.workbench.service.TranService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-08 19:02
 **/
@Controller
@RequestMapping("/transaction")
public class TranController {

    @Autowired
    UserService userService;

    @Resource
    CustomerService customerServiceImpl;

    @Resource
    TranService tranServiceImpl;

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView();
        List<User> userList = userService.gitUserList();
        mv.addObject("uList", userList);
        mv.setViewName("/transaction/save");
        return mv;
    }

    @RequestMapping("/getCustomerName")
    @ResponseBody
    public List<String> getCustomerName(String name) {

        return customerServiceImpl.getCustomerName(name);
    }


    //通过from表单提交 保存方法
    @RequestMapping("/save")
    public void save(Tran tran, String customerName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = UUIDUtil.getUUID();

        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        tran.setId(id);
        tran.setCreateTime(createTime);
        tran.setCreateBy(createBy);

        boolean flag = tranServiceImpl.save(tran, customerName);


        if (flag) {

            response.sendRedirect(request.getContextPath() + "/static/workbench/transaction/index.jsp");
        }
    }

    //跳转详情页   阶段和可能性
    @RequestMapping("/detail")
    public ModelAndView detail(String id,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        Tran t = tranServiceImpl.detail(id);


        //可能性
        String stage = t.getStage();

        ServletContext application=request.getServletContext();
        Map<String,String> pMap= (Map<String, String>) application.getAttribute("pMap");

        String possibility=pMap.get(stage);


        t.setPossibility(possibility);
        mv.addObject("t", t);

        mv.setViewName("/transaction/detail");
        return mv;
    }

    //根据id 获取交易历史列表
    @RequestMapping("/getHistoryListByTranId")
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(String tranId,HttpServletRequest request) {

        List<TranHistory> thList= tranServiceImpl.getHistoryListByTranId(tranId);

        ServletContext application=request.getServletContext();
        Map<String,String> pMap= (Map<String, String>) application.getAttribute("pMap");

        for (TranHistory th:thList){
            String stage=th.getStage();
            String possibility=pMap.get(stage);
            th.setPossibility(possibility);
        }

        return thList;
    }


    @RequestMapping("/changeStage")
    @ResponseBody
    public Map<String,Object> changeStage(String stage,String id,String money,String expectedDate,HttpServletRequest request) {



        String editBy =((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        //可能性
        ServletContext application=request.getServletContext();
        Map<String,String> pMap= (Map<String, String>) application.getAttribute("pMap");

        String possibility=pMap.get(stage);

        Tran tran =new Tran();

        tran.setId(id);
        tran.setStage(stage);
        tran.setMoney(money);
        tran.setExpectedDate(expectedDate);
        tran.setEditBy(editBy);
        tran.setEditTime(editTime);
        tran.setPossibility(possibility);


        boolean  flag=tranServiceImpl.changeStage(tran);



        Map<String,Object> map= new HashMap<>();

        map.put("success",flag);
        map.put("t",tran);


        return map;

    }

    //交易阶段的Echarts  交易阶段数量统计图
    @RequestMapping("/getCharts")
    @ResponseBody
    public Map<String,Object>  getCharts() {


        //返回值可以返回一个vo  map 也可以
        //通过map 打包
         return tranServiceImpl.getCharts();

    }



}
