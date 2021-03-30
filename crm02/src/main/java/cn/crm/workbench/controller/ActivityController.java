package cn.crm.workbench.controller;

import cn.crm.settings.domain.User;
import cn.crm.settings.service.UserService;
import cn.crm.utils.DateTimeUtil;
import cn.crm.utils.UUIDUtil;
import cn.crm.vo.PaginationVO;
import cn.crm.workbench.domain.ActivityRemark;
import cn.crm.workbench.domain.Clue;
import cn.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    //查询所有数据 tbl_user
    @RequestMapping("/gitUserList")
    @ResponseBody
    private List<User> gitUserList() {

        return userService.gitUserList();
    }

    //添加数据 tbl_activity
    @RequestMapping("/save")
    @ResponseBody
    private int save(HttpSession session, Activity activity) {

        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();

        //没用获取成功
        String createBy = ((User) session.getAttribute("user")).getName();

        activity.setId(id);
        activity.setCreateBy(createBy);
        activity.setCreateTime(createTime);


        return activityService.save(activity);
    }

    //数据分页
    @RequestMapping("/pageList")
    @ResponseBody
    private PaginationVO<Activity> pageList(String name,
                                            String owner,
                                            String startDate,
                                            String endDate, int pageNo, int pageSize) {


        //计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        /*复用率高 使用vo
        创建一个对象 封装到里面
         */
        return activityService.pageList(map);

    }

    //删除操作
    @RequestMapping("/delete")
    @ResponseBody
    private boolean delete(HttpServletRequest request) {

        String[] ids = request.getParameterValues("id");

        return activityService.delete(ids);
    }

    //修改操作
    @RequestMapping("/getUserListAndActivity")
    @ResponseBody
    private Map<String, Object> update(String id) {
        // 复用率不高 选择使用map
        return activityService.getUserListAndActivity(id);

    }

    //修改操作更新数据
    @RequestMapping("/update")
    @ResponseBody
    private int update(String id, HttpSession session, Activity activity) {


        String editTime = DateTimeUtil.getSysTime();

        //没用获取成功
        String editBy = ((User) session.getAttribute("user")).getName();

        activity.setId(id);
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);


        return activityService.update(activity);
    }


    //跳转详情页
    @RequestMapping("/detail")
    private ModelAndView detail(String id)  {
        ModelAndView mv = new ModelAndView();
        Activity a = activityService.detail(id);
//
//        request.setAttribute("a", a);
//
//        request.getRequestDispatcher("/static/workbench/activity/detail.jsp").forward(request, response);
//        return "detail";

        mv.addObject("a",a);
        mv.setViewName("/activity/detail");
        return mv;
    }


    @RequestMapping("/getRemarkListById")
    @ResponseBody
    private List<ActivityRemark> getRemarkListById(String activityId) {

        return activityService.getRemarkListById(activityId);
    }

    //删除备注
    @RequestMapping("/deleteRemark")
    @ResponseBody
    private boolean deleteRemark(String id) {

        return activityService.deleteRemark(id);
    }

    //添加备注
    @RequestMapping("/saveRemarkBnt")
    @ResponseBody
    private Map<String, Object> saveRemarkBnt(String activityId, String noteContent, HttpServletRequest request, ActivityRemark activityRemark) {

//        String activityId = request.getParameter("activityId");
//        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        activityRemark.setId(id);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setActivityId(activityId);
        activityRemark.setNoteContent(noteContent);

        boolean flag = activityService.saveRemark(activityRemark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        return map;
    }


    //修改备注
    @RequestMapping("/updateRemark")
    @ResponseBody
    private Map<String, Object> updateRemark(String id, String noteContent, HttpServletRequest request, ActivityRemark activityRemark) {


        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editFlag="1";

        activityRemark.setId(id);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);

        boolean flag = activityService.updateRemark(activityRemark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", activityRemark);
        return map;

    }


}
