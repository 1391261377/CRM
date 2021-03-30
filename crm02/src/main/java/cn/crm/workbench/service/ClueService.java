package cn.crm.workbench.service;

import cn.crm.workbench.domain.Clue;
import cn.crm.workbench.domain.Tran;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:13
 **/

public interface ClueService {


    boolean save(Clue clue);

    Clue detail(String id);

    boolean unbund(String id);


    boolean bund(String cId, String[] aId);



    boolean convert(String clueId, Tran tran, String createBy);
}
