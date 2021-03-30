package cn.crm.dao;

import cn.crm.workbench.domain.Tran;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TranDao {

    int save(Tran tran);

    Tran detail(String id);

    int changeStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
