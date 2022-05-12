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
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Entity.Tag;
import com.byb.houseservice.Entity.TagType;
import com.byb.houseservice.Service.*;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
//import com.fasterxml.jackson.databind.util.BeanUtil;
import com.byb.houseservice.Vo.TagVo;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

//import java.util.Collection;
import java.util.ArrayList;
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

    @Autowired
    private PostTagService postTagService;
    @Autowired
    private PostContactService postContactService;
    @Autowired
    private TagTypeService tagTypeService;
    @Autowired
    private ContactTypeService contactTypeService;
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

    @Override
    public Map<String, Object> houseById(int houseid) {
        Map<String, Object> result = new HashMap<>();
        try{
            HouseInfo houseInfo = baseMapper.selectById(houseid);
            result.put("houseInfo",houseInfo);

            Map<String,Object> select = new HashMap<>();
            select.put("houseId",houseid);

            Map<String,Object> tagList = postTagService.selectTag(select);
            List<Tag> tags = (List<Tag>) tagList.get("tagList");

            List<String> tagss = new ArrayList<>();
            for(Tag tag : tags){
                tagss.add(tagTypeService.tagNameById(tag.getTypeId()));
            }
            result.put("tagList",tagss);

            Map<String,Object> Contact = postContactService.selectContact(select);
            List<Contact> contacts = (List<com.byb.houseservice.Entity.Contact>) Contact.get("ContactList");
            Map<String,Object> contactss = new HashMap<>();
            for (Contact contact : contacts){
                String contactName = contactTypeService.TypeNameByid(contact.getTypeId());
                contactss.put(contactName,contact.getContent());
            }
            result.put("ContactList",contactss);

        }catch (Exception e){
            e.printStackTrace();;
            result.put("msg","失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectBycondition(Map<String, Object> selectCondition) {
        int pageSize;
        if (selectCondition.containsKey("pageSize"))
            pageSize = (int) selectCondition.get("pageSize");
        else pageSize = 10;
        int current;
        if (selectCondition.containsKey("current"))
            current = (int) selectCondition.get("current");
        else current = 1;
        Page<HouseInfo> houseInfoPage = new Page<>(current,pageSize);
        QueryWrapper<HouseInfo> queryWrapper = new QueryWrapper<>();
        if(selectCondition.containsKey("houseId")){
            int houseId = (int) selectCondition.get("houseId");
            queryWrapper.eq("houseId" , houseId);
        }
        if(selectCondition.containsKey("userId")){
            int userId = (int) selectCondition.get("userId");
            queryWrapper.eq("userId",userId);
        }
        if(selectCondition.containsKey("country")){
            String country = (String) selectCondition.get("country");
            queryWrapper.eq("country",country);
        }
        if(selectCondition.containsKey("province")){
            String province = (String) selectCondition.get("province");
            queryWrapper.eq("province",province);
        }
        if (selectCondition.containsKey("city")){
            String city = (String) selectCondition.get("city");
            queryWrapper.eq("city",city);
        }
        if(selectCondition.containsKey("deleteMark")){
            String de = (String) selectCondition.get("deleteMark");
            queryWrapper.eq("deleteMark",de);
        }
        else queryWrapper.eq("deleteMark","NO");
        if (selectCondition.containsKey("durationmin")){
            int duramin = (int) selectCondition.get("durationmin");
            queryWrapper.ge("duration",duramin);
        }
        if(selectCondition.containsKey("durationmax")){
            int duramax = (int) selectCondition.get("durationmax");
            queryWrapper.le("duration",duramax);
        }
        if (selectCondition.containsKey("guestmin")){
            int guestmin = (int) selectCondition.get("guestmin");
            queryWrapper.ge("guest",guestmin);
        }
        if (selectCondition.containsKey("guestmax")){
            int guestmax = (int) selectCondition.get("guestmax");
            queryWrapper.le("guest",guestmax);
        }
        if (selectCondition.containsKey("pets")){
            String pets = (String) selectCondition.get("pets");
            queryWrapper.eq("pets",pets);
        }
        if (selectCondition.containsKey("actice")){
            String actice = (String) selectCondition.get("actice");
            queryWrapper.eq("actice",actice);
        }
        if (selectCondition.containsKey("sort")){
            String sort = (String) selectCondition.get("sort");
            if(selectCondition.containsKey("sortop")){
                String sortop = (String) selectCondition.get("sortop");
                if(sortop.equals("asc")){
                    queryWrapper.orderByAsc(sort);
                }
            }
            queryWrapper.orderByDesc(sort);
        }
//        if (selectCondition.containsKey("contactlist")){
//            List<Integer> list = (List<Integer>) selectCondition.get("contactlist");
//
//        }

        Page<HouseInfo> page = this.page(houseInfoPage,queryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("houseinfo",page.getRecords());
        return result;
    }

}
