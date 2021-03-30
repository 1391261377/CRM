package cn.crm.dao;

import cn.crm.settings.domain.DicType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:24
 **/
@Repository
public interface DicTypeDao {
    List<DicType> getTypeList();
}
