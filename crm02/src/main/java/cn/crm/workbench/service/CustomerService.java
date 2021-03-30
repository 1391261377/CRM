package cn.crm.workbench.service;

import cn.crm.vo.PaginationVO;
import cn.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-09 14:03
 **/
public interface CustomerService {
    List<String> getCustomerName(String name);

    PaginationVO<Customer> pageList(Map<String, Object> map);
}
