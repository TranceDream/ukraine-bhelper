package com.byb.houseservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Vo.ContactVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 13:38
 */
@Repository
public interface PostContactService extends IService<Contact> {

    Map<String,Object> addPostContact(List<ContactVo> list);
    Map<String,Object> updateContact(ContactVo contactVo);
    Map<String,Object> deleteContact(int contactId);
    Map<String,Object> selectContact(Map<String,Object> selectCondition);


}
