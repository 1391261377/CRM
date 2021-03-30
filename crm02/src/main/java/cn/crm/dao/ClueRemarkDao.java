package cn.crm.dao;

import cn.crm.workbench.domain.ClueRemark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClueRemarkDao {

    List<ClueRemark> getListCludId(String clueId);

    int delete(ClueRemark clueRemark);
}
