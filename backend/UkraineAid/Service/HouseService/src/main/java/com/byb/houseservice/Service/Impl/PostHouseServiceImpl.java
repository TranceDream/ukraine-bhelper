package com.byb.houseservice.Service.Impl;

//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.HouseInfoMapper;
import com.byb.houseservice.Entity.TagType;
import com.byb.houseservice.Service.PostHouseService;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
//import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

//import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> result = new HashMap<>();
        try{
            HouseInfo houseinfo = new HouseInfo();
            BeanUtils.copyProperties(houseinfoVo,houseinfo);
            houseinfo.setActive("YES");
            System.out.println(houseinfo);

            baseMapper.insert(houseinfo);

            result.put("data",houseinfo.getHouseId());
            result.put("msg","提交成功");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","提交失败");
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
            result.put("msg","修改成功");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","修改失败");
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
            result.put("msg","删除成功");
            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","删除失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> selcetHouse(Map<String, Object> selectCondition) {
        Map<String, Object> result = new HashMap<>();
        try{
            List<HouseInfo> houseInfoList = baseMapper.selectByMap(selectCondition);
            System.out.println(selectCondition);
            result.put("data",houseInfoList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","失败");
        }
        return result;
    }

}
