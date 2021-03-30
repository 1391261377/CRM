package cn.crm.workbench.service.impl;

import cn.crm.dao.CustomerDao;
import cn.crm.dao.TranDao;
import cn.crm.dao.TranHistoryDao;
import cn.crm.utils.DateTimeUtil;
import cn.crm.utils.UUIDUtil;
import cn.crm.workbench.domain.Customer;
import cn.crm.workbench.domain.Tran;
import cn.crm.workbench.domain.TranHistory;
import cn.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-08 19:00
 **/
@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranDao tranDao;

    @Resource
    private TranHistoryDao tranHistoryDao;


    @Resource
    CustomerDao customerDao;

    @Override
    public boolean save(Tran tran, String customerName) {

        boolean flag = true;
        Customer customer = customerDao.getCustomerByName(customerName);

        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(tran.getCreateBy());
            customer.setName(customerName);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());

            int count = customerDao.save(customer);
            if (count != 1) {
                flag = false;
            }
        }

        tran.setCustomerId(customer.getId());

        int count2 = tranDao.save(tran);
        if (count2 != 1) {
            flag = false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(customer.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(tranHistory);
        if (count3 != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {

        return tranDao.detail(id);
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {
        return tranHistoryDao.getHistoryListByTranId(tranId);
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag=true;


        //改变交易阶段
        int count=tranDao.changeStage(tran);
        if (count != 1) {

            flag=false;
        }

        //生成一条交易阶段历史

        TranHistory tranHistory=new TranHistory();
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setTranId(tran.getId());

        //添加交易历史
        int count2=tranHistoryDao.save(tranHistory);
        if (count2 != 1) {

            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        //取得总数量
        int total=tranDao.getTotal();

        List<Map<String,Object>> dataList= tranDao.getCharts();
        //取得dataList

        Map<String, Object> map = new HashMap<>();

        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }
}
