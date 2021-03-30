package cn.crm.workbench.service.impl;

import cn.crm.dao.ActivityDao;
import cn.crm.dao.ActivityRemarkDao;
import cn.crm.dao.UserDao;
import cn.crm.settings.domain.User;
import cn.crm.vo.PaginationVO;
import cn.crm.workbench.domain.ActivityRemark;
import cn.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-01-28 15:07
 **/
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    ActivityRemarkDao activityRemarkDao;

    @Autowired
    UserDao userDao;

    @Override
    public int save(Activity activity) {


        return activityDao.save(activity);
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        int total = activityDao.getTotalByCondition(map);
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;
        //查询需要删除备注的条数
        int count = activityRemarkDao.getCountById(ids);

        //删除备注 返回受到影响的条数
        int count2 = activityRemarkDao.deleteById(ids);

        if (count != count2) {

            flag = false;
        }
        //删除市场活动

        int count3 = activityDao.delete(ids);
        if (count3 != ids.length) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        List<User> uList=userDao.gitUserList();

        Activity  a=activityDao.getById(id);
        Map<String, Object> map= new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public int update(Activity activity) {
        return activityDao.update(activity);
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> getRemarkListById(String activityId) {
        return activityRemarkDao.getRemarkListById(activityId);
    }

    @Override
    public boolean deleteRemark(String id) {
        return activityRemarkDao.deleteRemark(id);
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {

        boolean flag=true;
         int  count=activityRemarkDao.saveRemark(activityRemark);
        if (count != 1) {
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.updateRemark(activityRemark);
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {

        return activityDao.getActivityListByClueId(clueId);

    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {

        return activityDao.getActivityListByNameAndNotByClueId(map);
    }

    @Override
    public List<Activity> getActivityByNameList(String aname) {
        return activityDao.getActivityByNameList(aname);
    }
}
