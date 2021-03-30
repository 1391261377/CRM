package cn.crm.workbench.service;

import cn.crm.vo.PaginationVO;
import cn.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-01-28 15:06
 **/

public interface ActivityService {
    int save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);


    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListById(String activityId);

    boolean deleteRemark(String id);


    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityByNameList(String aname);
}
