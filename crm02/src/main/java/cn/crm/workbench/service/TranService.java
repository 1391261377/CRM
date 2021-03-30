package cn.crm.workbench.service;

import cn.crm.workbench.domain.Tran;
import cn.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-08 19:00
 **/
public interface TranService {
    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran tran);

    Map<String, Object> getCharts();
}
