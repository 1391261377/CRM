package cn.crm.dao;

import cn.crm.workbench.domain.TranHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
