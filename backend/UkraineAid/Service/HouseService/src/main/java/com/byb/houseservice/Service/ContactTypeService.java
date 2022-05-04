package com.byb.houseservice.Service;

import com.byb.houseservice.Entity.ContactType;
import com.byb.houseservice.Vo.ContactTypeVo;
import com.byb.houseservice.Vo.ContactVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 13:41
 */
@Repository
public interface ContactTypeService {
    Map<String,Object> addContactType(ContactTypeVo contactTypeVo);
    Map<String,Object> updateContactType(ContactTypeVo contactTypeVo);
    Map<String,Object> deleteContactType(int contactTypeId);

}
