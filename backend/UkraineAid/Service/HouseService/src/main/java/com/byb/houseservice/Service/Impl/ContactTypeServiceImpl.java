package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.ContactTypeMapper;
import com.byb.houseservice.Entity.ContactType;
import com.byb.houseservice.Service.ContactTypeService;
import org.springframework.stereotype.Service;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 14:13
 */

@Service
public class ContactTypeServiceImpl extends ServiceImpl<ContactTypeMapper, ContactType>
        implements ContactTypeService {

}
