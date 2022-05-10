package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.ContactTypeMapper;
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Entity.ContactType;
import com.byb.houseservice.Service.ContactTypeService;
import com.byb.houseservice.Vo.ContactTypeVo;
import com.byb.houseservice.Vo.ContactVo;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 14:13
 */

@Service
public class ContactTypeServiceImpl extends ServiceImpl<ContactTypeMapper, ContactType>
        implements ContactTypeService {

    @Override
    public Map<String, Object> addContactType(ContactTypeVo contactTypeVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            ContactType contactType =new ContactType();
            BeanUtils.copyProperties(contactTypeVo,contactType);
            baseMapper.insert(contactType);

            result.put("msg","Success!");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure!");
        }
        return result;
    }

    @Override
    public Map<String, Object> updateContactType(ContactTypeVo contactTypeVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            ContactType contactType= new ContactType();
            BeanUtils.copyProperties(contactTypeVo,contactType);
            baseMapper.updateById(contactType);

            result.put("msg","Success!");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure");
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteContactType(int contactTypeId) {
        Map<String, Object> result = new HashMap<>();
        try{
            ContactType contactType = new ContactType();
            contactType.setTypeId(contactTypeId);
            contactType.setDeleteMark("YES");

//            System.out.println(houseinfo);
            baseMapper.updateById(contactType);

            result.put("data",contactTypeId);
            result.put("msg","Success!");
//            System.out.println("duiduidui");
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectContactType(Map<String, Object> selectCondition,boolean isadmin) {
        Map<String, Object> result = new HashMap<>();
        if(!isadmin) selectCondition.put("deleteMark","NO");
        try{
            List<ContactType> ContactTypeList = baseMapper.selectByMap(selectCondition);
            result.put("data",ContactTypeList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Failure");
        }
        return result;
    }

    @Override
    public String TypeNameByid(int contactTypeId) {
        ContactType contactType = baseMapper.selectById(contactTypeId);
        return contactType.getContactName();
    }

}
