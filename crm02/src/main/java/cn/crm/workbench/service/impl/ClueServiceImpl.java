package cn.crm.workbench.service.impl;

import cn.crm.dao.*;
import cn.crm.utils.DateTimeUtil;
import cn.crm.utils.UUIDUtil;
import cn.crm.workbench.domain.*;
import cn.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: crm
 * @description
 * @author: aoligei
 * @create: 2021-02-01 20:13
 **/
@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;


    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Override
    public boolean save(Clue clue) {
        boolean flag = true;

        int count = clueDao.save(clue);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Clue detail(String id) {
        return clueDao.detail(id);
    }


    @Override
    public boolean unbund(String id) {
        boolean flag = true;

        int count = clueActivityRelationDao.unbund(id);

        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bund(String cId, String[] aId) {
        boolean flag = true;

        for (String aid : aId) {

            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cId);

            int count = clueActivityRelationDao.bund(car);


            if (count != 1) {
                flag = false;
            }
        }


        return flag;
    }


    @Override
    public boolean convert(String clueId, Tran tran, String createBy) {

        String createTime = DateTimeUtil.getSysTime();
//      String createBy = ((User) request.getSession().getAttribute("user")).getName();   转request 不合理

        boolean flag = true;

        //通过线索id 查询线索对象
        Clue clue = clueDao.getById(clueId);

        //通过线索信息获取客户信息 当客户不存在 新建客户
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        //没有客户新建一个
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            //添加客户
            int count = customerDao.save(customer);
            if (count != 1) {
                flag = false;
            }

        }

        //通过线索对象提起联系人信息 保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());

        contacts.setOwner(clue.getOwner());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        //添加联系人
        int count2 = contactsDao.save(contacts);
        if (count2 != 1) {
            flag = false;
        }

        //线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getListCludId(clueId);
        //取出每一条线索备注
        for (ClueRemark clueRemark : clueRemarkList) {
            //取出备注信息
            String nextContact = clueRemark.getNoteContent();

            //创建客户备注对象，添加客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(nextContact);

            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1) {
                flag = false;
            }

            //创建联系人备注 添加联系人

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(nextContact);

            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1) {
                flag = false;
            }

        }

        //线索和市场活动的关系转换到联系人和市场活动的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList
        ) {

            String activityId = clueActivityRelation.getActivityId();
            //创建联系人与市场活动的关联关系对象 让第三步生成的联系人与市场活动做关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());

            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1) {
                flag = false;
            }

        }
        //如果有创建交易需求 创建一条交易
        if (tran != null) {
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setContactsId(contacts.getId());
            //添加交易
            int count6 = tranDao.save(tran);
            if (count6 != 1) {
                flag = false;
            }

            //如果创建了交易 则创建一条交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());

            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1) {
                flag = false;
            }
        }


        //删除线索备注
        for (ClueRemark clueRemark : clueRemarkList) {
            int count8=clueRemarkDao.delete(clueRemark);
            if (count8 != 1) {
                flag = false;
            }
        }
        //删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList
        ){

            int count9=clueActivityRelationDao.delete(clueActivityRelation);
            if (count9 != 1) {
                flag = false;
            }
        }

        //删除线索
        int count10=clueDao.delete(clueId);
        if (count10 != 1) {
            flag = false;
        }

        return flag;
    }
}
