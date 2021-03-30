package cn.crm.settings.service.impl;

import cn.crm.dao.DicTypeDao;
import cn.crm.dao.DicValueDao;
import cn.crm.settings.domain.DicType;
import cn.crm.settings.domain.DicValue;
import cn.crm.settings.service.DicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:27
 **/
@Service
public class DicServiceImpl implements DicService {



    @Autowired
    DicTypeDao dicTypeDao;
    @Autowired
    DicValueDao dicValueDao;
    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map= new HashMap<>();

        //将字典类型列表全部取出
        List<DicType> dicTypeList=dicTypeDao.getTypeList();

        for (DicType dt : dicTypeList) {

            String typeCode=dt.getCode();


            List<DicValue> dvList=dicValueDao.getListByCode(typeCode);

            map.put(typeCode+"List",dvList);
        }
        return map;
    }
}
