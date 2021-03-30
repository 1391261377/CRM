package cn.crm.dao;

import cn.crm.workbench.domain.ActivityRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-01-28 15:51
 **/
@Repository
public interface ActivityRemarkDao {
    int getCountById(String[] ids);

    int deleteById(String[] ids);

    List<ActivityRemark> getRemarkListById(String activityId);

    boolean deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
