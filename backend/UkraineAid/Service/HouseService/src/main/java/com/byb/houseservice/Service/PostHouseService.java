package com.byb.houseservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Service.Impl.PostHouseServiceImpl;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
import org.springframework.stereotype.Repository;


import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @date 2022/5/3 23:17
 */

@Repository
public interface PostHouseService extends IService<HouseInfo> {


    Map<String ,Object> addpostHouseInfo(HouseinfoVo houseinfovo);
    Map<String ,Object> updateHouseInfo(HouseinfoVo houseinfoVo);
    Map<String ,Object> deleteHouseInfo(int houseid);
    Map<String,Object> selcetHouse(Map<String,Object> selectCondition);
    Map<String,Object> houseById(int houseid);
    Map<String,Object> selectBycondition(Map<String,Object> selectCondition);

//    Map<String,Object> testpage(HouseinfoVo houseinfoVo);
//    Map<String ,Object> updateHouseInfo(HouseinfoVo houseinfoVo);

}
