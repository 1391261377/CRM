package cn.crm.dao;

import cn.crm.workbench.domain.ClueActivityRelation;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(ClueActivityRelation clueActivityRelation);
}
