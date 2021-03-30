package cn.crm.dao;

import cn.crm.workbench.domain.CustomerRemark;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRemarkDao {

    int save(CustomerRemark customerRemark);
}
