package cn.crm.workbench.controller;


import cn.crm.settings.domain.User;
import cn.crm.settings.service.UserService;
import cn.crm.utils.DateTimeUtil;
import cn.crm.utils.UUIDUtil;
import cn.crm.workbench.domain.Clue;
import cn.crm.workbench.domain.Tran;
import cn.crm.workbench.service.ActivityService;
import cn.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-01-28 15:10
 **/

@Controller
@RequestMapping("/clue")
//线索控制器
public class ClueController {


    @Autowired
    UserService userService;

    @Autowired
    ClueService clueService;

    @Autowired
    ActivityService activityService;

    @RequestMapping("/gitUserList")
    @ResponseBody
    public List<User> gitUserList() {

        return userService.gitUserList();
    }


    @RequestMapping("/save")
    @ResponseBody
    public boolean save(HttpServletRequest request, Clue clue) {
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        clue.setId(id);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        return clueService.save(clue);
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String id) {
        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.detail(id);

        mv.addObject("clue", clue);
        mv.setViewName("/clue/detail");
        return mv;

    }

    @RequestMapping("/getActivityList")
    @ResponseBody
    public List<Activity> getActivityList(String clueId) {


        return activityService.getActivityListByClueId(clueId);
    }

    @RequestMapping("/unbund")
    @ResponseBody
    public boolean unbund(String id) {


        return clueService.unbund(id);
    }

    @RequestMapping("/getActivityListByNameAndNotByClueId")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String activityName, String clueId) {

        Map<String, String> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);

        return activityService.getActivityListByNameAndNotByClueId(map);
    }

//    @RequestMapping("/bund")
//    @ResponseBody
//    public boolean bund(HttpServletRequest request) {
//
//        String cid=request.getParameter("cid");
//        String[] aids=request.getParameterValues("aid");
//
//        return clueService.bund(cid,aids);
//
//    }

    @RequestMapping("/bund")
    @ResponseBody
    public boolean bund(String cId, String[] aId) {
        return clueService.bund(cId, aId);

    }


    @RequestMapping("/getActivityByNameList")
    @ResponseBody
    public List<Activity> getActivityByNameList(String aname) {

        return activityService.getActivityByNameList(aname);
    }


    @RequestMapping("/convert")
    public ModelAndView convert(String clueId, String flag, HttpServletRequest request,
                                String money, String name, String expectedDate, String stage, String activityId
    ) {

        Tran tran = null;
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        //需要创建交易
        if ("a".equals(flag)) {

            tran = new Tran();
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            tran.setId(id);
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
        }

        ModelAndView mv = new ModelAndView();

        boolean flag1 = clueService.convert(clueId, tran, createBy);
        if (flag1) {
            mv.setViewName("/clue/index");
        }

        return mv;
    }


}