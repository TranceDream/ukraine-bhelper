package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.ContactMapper;
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Service.PostContactService;
import com.byb.houseservice.Vo.ContactVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 13:43
 */
@Service
public class PostContactServiceImpl extends ServiceImpl<ContactMapper, Contact>
        implements PostContactService {

    @Override
    public Map<String, Object> addPostContact(List<ContactVo> listContact) {
        Map<String, Object> result = new HashMap<>();
        try{
            Contact contact = new Contact();
            for (ContactVo contactVo: listContact){
                BeanUtils.copyProperties(contactVo,contact);
                baseMapper.insert(contact);
            }
            result.put("msg","Success!");
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }

        return result;
    }

    @Override
    public Map<String, Object> updateContact(ContactVo contactVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            Contact contact = new Contact();
            BeanUtils.copyProperties(contactVo,contact);
            baseMapper.updateById(contact);
            result.put("msg","Success!");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteContact(int contactId) {
        Map<String, Object> result = new HashMap<>();
        try{
            Contact contact = new Contact();
            contact.setContactId(contactId);
            contact.setDeleteMask("YES");

//            System.out.println(houseinfo);
            baseMapper.updateById(contact);

            result.put("data",contact.getContactId());
            result.put("msg","Success!");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectContact(Map<String, Object> selectCondition) {
        Map<String, Object> result = new HashMap<>();
        selectCondition.put("deleteMask","NO");
        try{
            List<Contact> ContactList = baseMapper.selectByMap(selectCondition);
            result.put("ContactList",ContactList);
            result.put("number",ContactList.size());
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectContactAll(Map<String, Object> selectCondition) {
        Map<String, Object> result = new HashMap<>();
        try{
            List<Contact> ContactList = baseMapper.selectByMap(selectCondition);
            result.put("ContactList",ContactList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }
        return result;
    }


}
