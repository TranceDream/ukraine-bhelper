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

            result.put("msg","提交成功");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","提交失败");
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

            result.put("msg","修改成功");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","修改失败");
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
            result.put("msg","删除成功");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","删除失败");
        }
        return result;
    }

}
