package com.byb.houseservice.Service.Impl;

//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.BaseUtil.Utils.Result;
import com.byb.BaseUtil.Utils.UploadPicUtil;
import com.byb.houseservice.Dao.HouseInfoMapper;
import com.byb.houseservice.Entity.*;
import com.byb.houseservice.Service.*;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
//import com.fasterxml.jackson.databind.util.BeanUtil;
import com.byb.houseservice.Vo.TagVo;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;

import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

//import java.util.Collection;
import java.io.*;
import java.util.*;
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
    @Autowired
    private FilePicService filePicService;
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
            result.put("code",200);
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("code",400);
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
            List<TagVo> tagVoList = new ArrayList<>();
            for(Tag tag : tags){
                String tagName =  tagTypeService.tagNameById(tag.getTypeId());
                TagVo tagVo = new TagVo();
                BeanUtils.copyProperties(tag,tagVo);
                tagVo.setTagName(tagName);
                tagVoList.add(tagVo);
            }
            result.put("tagList",tagVoList);

            Map<String,Object> Contact = postContactService.selectContact(select);
            List<Contact> contacts = (List<com.byb.houseservice.Entity.Contact>) Contact.get("ContactList");
//            Map<String,Object> contactss = new HashMap<>();
            List<ContactVo> contactVoList = new ArrayList<>();

            for (Contact contact : contacts){
                String contactName = contactTypeService.TypeNameByid(contact.getTypeId());
//                contactss.put(contactName,contact.getContent());
                ContactVo contactVo =new ContactVo();
                BeanUtils.copyProperties(contact,contactVo);
                contactVo.setContactName(contactName);
                contactVoList.add(contactVo);
            }
            result.put("ContactList",contactVoList);

            FileName fileName = new FileName();
            fileName.setHouseId(houseid);
            Map<String,Object> fileList =  filePicService.reHousePic(fileName);
            result.put("filePicList",fileList.get("fileNames"));

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
            queryWrapper.ge("guests",guestmin);
        }
        if (selectCondition.containsKey("guestmax")){
            int guestmax = (int) selectCondition.get("guestmax");
            queryWrapper.le("guests",guestmax);
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
        List<HouseInfo> houseInfoList = page.getRecords();
        List<HouseinfoVo> houseinfoVoList = new ArrayList<>();
        for (HouseInfo houseinfo : houseInfoList){
            HouseinfoVo houseinfoVo = new HouseinfoVo();
            BeanUtils.copyProperties(houseinfo,houseinfoVo);
            FileName fileName1 = new FileName();
            fileName1.setHouseId(houseinfo.getHouseId());
            Map<String, Object> stringObjectMap = filePicService.reHousePic(fileName1);
            List<String> fileNames = (List<String>) stringObjectMap.get("fileNames");
            houseinfoVo.setFileNames(fileNames);
            houseinfoVoList.add(houseinfoVo);
        }
//        int count  = baseMapper.selectCount(queryWrapper);
        Map<String, Object> result = new HashMap<>();

        result.put("houseinfo",houseinfoVoList);
        result.put("count",baseMapper.selectCount(queryWrapper));
        return result;
    }

    @Override
    public Map<String, Object> addHouse(HouseinfoVo houseinfoVo, List<TagVo> tagVoList, List<ContactVo> contactVoList,
                                        List<MultipartFile> multipartFiles) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> resultHouse =  this.addpostHouseInfo(houseinfoVo);
        if ((int) resultHouse.get("code") != 200) {
            result.put("msg","ERROR in insert house!");
            return  result;
        }
        int houseId = (int) resultHouse.get("houseId");

        for (TagVo tagVo : tagVoList){
            tagVo.setHouseId(houseId);
        }
        Map<String,Object> resultTag = postTagService.addPostTag(tagVoList);
        if ((int) resultTag.get("code") != 200){
            result.put("msg","ERROR in insert tags!");
            return result;
        }

        for (ContactVo contactVo : contactVoList){
            contactVo.setHouseId(houseId);
        }
        Map<String,Object> resultContact = postContactService.addPostContact(contactVoList);
        if ((int) resultContact.get("code") != 200){
            result.put("msg","ERROR in insert contact");
            return result;
        }

        //新文件名
        StringBuilder newFileName;
        //全部文件名，若多个，则逗号分隔
        StringBuilder allFileNames = new StringBuilder();
        //上传路径
        String fileSavePath = "/Ukother/Ukpic/housePic/";
        //若文件夹不存在，则创建文件夹
        //test.txt不会创建，故给任意值占位即可
        File parentFile = new File(fileSavePath + "test.txt").getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //遍历所有文件
        for (MultipartFile file : multipartFiles) {
            //得到原始文件名
            newFileName = new StringBuilder(file.getOriginalFilename());
            //查询文件名中点号所处位置
            int i = newFileName.lastIndexOf(".");
            //若点号位置大于0，则文件名正常
            if (i > 0) {
                //在点号前拼接UUID防止文件重名
                newFileName.insert(i, "_" + UUID.randomUUID().toString().substring(0, 5));
            } else {
                //若点号位置小于等于0，则文件名不规范
                result.put("msg","ERROR IN PIC");
                return result;
            }
            try {
                // 保存文件
                file.transferTo(new File(fileSavePath + newFileName));
                //若全部文件名为空，则此为第一个文件，直接追加此文件名
                if (StringUtils.isBlank(allFileNames.toString())) {
                    allFileNames.append(newFileName);
                } else {
                    //若全部文件名不为空，则追加逗号和文件名
                    allFileNames
                            .append(",")
                            .append(newFileName)
                            .toString();
                }
            } catch (IOException e) {
                result.put("msg","ERROR IN PIC");
                return result;
            }
        }
        List<String> fileNameList = Arrays.asList(allFileNames.toString().split(","));
        for (String str : fileNameList){
            FileName fileName1 = new FileName();
            fileName1.setFilePath(str);
            fileName1.setHouseId(houseId);
            filePicService.uploadHousePic(fileName1);
        }


        result.put("msg","Success");
        result.put("fileNames",fileNameList);
        result.put("filePath", fileSavePath);
        return result;
    }



}
