package com.byb.houseservice.Service.Impl;

//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.HouseInfoMapper;
import com.byb.houseservice.Entity.TagType;
import com.byb.houseservice.Service.PostHouseService;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
//import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

//import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
//import java.util.function.Function;

/**
 * @author zjt
 * @date 2022/5/4 0:05
 */
@Service
public class PostHouseServiceImpl extends ServiceImpl<HouseInfoMapper,HouseInfo>
        implements PostHouseService {


    @Override
    public Map<String, Object> addpostHouseInfo(HouseinfoVo houseinfoVo) {
        System.out.println(houseinfoVo);
        Map<String, Object> result = new HashMap<>();
        try{
            HouseInfo houseinfo = new HouseInfo();
            BeanUtils.copyProperties(houseinfoVo,houseinfo);
            houseinfo.setActive("YES");
            System.out.println(houseinfo);
            baseMapper.insert(houseinfo);

            result.put("houseId",houseinfo.getHouseId());
            result.put("msg","A successful submission");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Fail to submit!PARAMETER ERROR");
        }
        return result;
    }

    @Override
    public Map<String, Object> updateHouseInfo(HouseinfoVo houseinfoVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            HouseInfo houseinfo = new HouseInfo();
            BeanUtils.copyProperties(houseinfoVo,houseinfo);

//            System.out.println(houseinfo);
            baseMapper.updateById(houseinfo);

            result.put("data",houseinfo.getHouseId());
            result.put("msg","Succeeded in modifying data");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Fail to submit!PARAMETER ERROR");
        }

        return result;
    }

    @Override
    public Map<String, Object> deleteHouseInfo(int house) {
        Map<String, Object> result = new HashMap<>();
        try{
            HouseInfo houseinfo = new HouseInfo();
            houseinfo.setHouseId(house);
            houseinfo.setDeleteMark("YES");

//            System.out.println(houseinfo);
            baseMapper.updateById(houseinfo);

            result.put("data",houseinfo.getHouseId());
            result.put("msg","Delete the success!");


        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","Delete failed!");
        }
        return result;
    }

    @Override
    public Map<String, Object> selcetHouse(Map<String, Object> selectCondition) {

        if(!selectCondition.containsKey("pageNo")){
            selectCondition.put("pageNo",1);
        }
        if(!selectCondition.containsKey("pageSize")) {
            selectCondition.put("pageSize",10);
        }
        int pageSize = (int) selectCondition.get("pageSize");
        int pageNo = (int) selectCondition.get("pageNo");
        selectCondition.remove("pageNo");
        selectCondition.remove("pageSize");
//        selectCondition.put("start",(pageNo-1)*pageSize);
//        selectCondition.put("size",pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("pageNo",pageNo);
        result.put("pageSize",pageSize);

        try{
            List<HouseInfo> houseInfoList = baseMapper.selectByMap(selectCondition);
            houseInfoList=houseInfoList.subList((pageNo-1)*pageSize,pageNo*pageSize);
            System.out.println(selectCondition);
            result.put("data",houseInfoList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","失败");
        }
        return result;
    }

}
