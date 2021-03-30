package cn.crm.dao;


import cn.crm.workbench.domain.Clue;
import org.springframework.stereotype.Repository;

@Repository
public interface ClueDao {


    int save(Clue clue);

    Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);
}
