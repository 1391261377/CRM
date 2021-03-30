package cn.crm.workbench.controller;

import cn.crm.vo.PaginationVO;
import cn.crm.workbench.domain.Customer;
import cn.crm.workbench.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-09 20:09
 **/
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    CustomerService customerServiceImpl;


    //数据分页
    @RequestMapping("/pageList")
    @ResponseBody
    private PaginationVO<Customer> pageList(String name,
                                            String owner,
                                            String phone,
                                            String website, int pageNo, int pageSize) {


        //计算出略过的记录数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("owner", owner);
        map.put("phone", phone);
        map.put("website", website);

        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        // 复用率高 选择使用vo 创建一个对象 封装到里面
        return customerServiceImpl.pageList(map);

    }
}
