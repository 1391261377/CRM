package cn.crm.dao;

import cn.crm.settings.domain.DicValue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:25
 **/
@Repository
public interface DicValueDao {
    List<DicValue> getListByCode(String typeCode);
}
