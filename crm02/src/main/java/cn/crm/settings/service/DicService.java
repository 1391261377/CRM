package cn.crm.settings.service;

import cn.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:26
 **/
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
