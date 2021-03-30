package cn.crm.dao;

import cn.crm.workbench.domain.ContactsActivityRelation;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsActivityRelationDao {

   int save(ContactsActivityRelation contactsActivityRelation);
}
