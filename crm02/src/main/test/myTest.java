import cn.crm.dao.*;
import cn.crm.settings.domain.DicType;
import cn.crm.settings.domain.DicValue;
import cn.crm.settings.service.DicService;
import cn.crm.utils.ServiceFactory;
import cn.crm.utils.UUIDUtil;
import cn.crm.workbench.domain.Clue;
import cn.crm.workbench.domain.ClueActivityRelation;
import cn.crm.workbench.domain.Tran;
import cn.crm.workbench.service.ClueService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-01-27 12:59
 **/
public class myTest {

    @Test
    public void findByUsername() {
        Map<String, List<DicValue>> map= new HashMap<>();

        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
        DicValueDao dicValueDao = (DicValueDao) ac.getBean("dicValueDao");
        DicTypeDao dicTypeDao = (DicTypeDao) ac.getBean("dicTypeDao");

        List<DicType>  dicTypes=dicTypeDao.getTypeList();
        String dt =null;
        for (DicType d :dicTypes
                ) {

            dt=d.getCode();
            System.out.println(d);
            List<DicValue> dicValues=dicValueDao.getListByCode(dt);

            map.put(dt,dicValues);
        }


        Set<String> set=map.keySet();
        for (String key :set
        ) {
            System.out.println(key+map.get(key));
        }
    }



    @Test
    public void test02() {
        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
        UserDao user= (UserDao) ac.getBean("userDao");

        user.gitUserList();


    }

    @Test
    public void test03() {
        Map<String, List<DicValue>> map= new HashMap<>();

        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
        DicService dicService = (DicService) ac.getBean("dicServiceImpl");
        map=dicService.getAll();



    }
    @Test
    public void test04() {
        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
        ClueActivityRelationDao clueActivityRelationDao= (ClueActivityRelationDao) ac.getBean("clueActivityRelationDao");

        ClueActivityRelation car = new ClueActivityRelation();

        car.setClueId("7d7e9bb4b76d4460b9ad4cb80da93535");
        car.setActivityId("ed992d45486447f2924c0a3b163cfbb1");
        car.setId(UUIDUtil.getUUID());

        clueActivityRelationDao.bund(car);
    }


    @Test
    public void test05() {
        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);



        ClueActivityRelationDao clueActivityRelationDao= (ClueActivityRelationDao) ac.getBean("clueActivityRelationDao");
        ClueService clueService= (ClueService) ac.getBean("clueServiceImpl");

        ClueActivityRelation car = new ClueActivityRelation();

        car.setClueId("7d7e9bb4b76d4460b9ad4cb80da93535");
        car.setActivityId("ed992d45486447f2924c0a3b163cfbb1");
        car.setId(UUIDUtil.getUUID());

        clueActivityRelationDao.bund(car);


    }

    @Test
    public void test06() {

        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
         TranDao tran = (TranDao) ac.getBean("tranDao");
         tran.detail("c8940420dc5249f8ac04f5eff652f431");



    } @Test
    public void test07() {

        String config = "applicationContext.xml";
        ApplicationContext ac= new ClassPathXmlApplicationContext(config);
         ClueDao  clueDao = (ClueDao) ac.getBean("clueDao");

         clueDao.detail("eab78376585e48e7bc3d8a5ec0c82d23");


    }

}
