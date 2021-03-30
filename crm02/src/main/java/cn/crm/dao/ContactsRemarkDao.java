package cn.crm.dao;

import cn.crm.workbench.domain.ContactsRemark;
import cn.crm.workbench.domain.CustomerRemark;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRemarkDao {


    int save(ContactsRemark contactsRemark);
}
