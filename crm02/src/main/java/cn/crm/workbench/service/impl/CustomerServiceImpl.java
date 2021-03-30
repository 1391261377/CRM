package cn.crm.workbench.service.impl;

import cn.crm.dao.CustomerDao;
import cn.crm.vo.PaginationVO;

import cn.crm.workbench.domain.Customer;
import cn.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-09 14:03
 **/
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerDao customerDao;


    @Override
    public List<String> getCustomerName(String name) {

        return  customerDao.getCustomerName(name);
    }

    @Override
    public PaginationVO<Customer> pageList(Map<String, Object> map) {
        int total = customerDao.getTotalByCondition(map);
        List<Customer> dataList = customerDao.getActivityListByCondition(map);

        PaginationVO<Customer> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }
}
