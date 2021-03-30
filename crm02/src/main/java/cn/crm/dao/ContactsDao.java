package cn.crm.dao;

import cn.crm.workbench.domain.Contacts;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsDao {

    int save(Contacts contacts);
}
