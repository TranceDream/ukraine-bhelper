package com.byb.houseservice.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.BaseUtil.Utils.UploadPicUtil;
import com.byb.houseservice.Entity.FileName;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Service.*;
import com.byb.houseservice.Vo.*;
import com.byb.openfeign.Client.ReportClient;
import com.byb.openfeign.Form.FormGeneration;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/house")
public class PostController {

    @PostMapping("/test")
    public Result<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "welcome to house module");
        return new Result<>(map, Result.SUCCESS);
    }


    @Autowired
    private PostHouseService postHouseService;

    @Autowired
    private PostContactService postContactService;

    @Autowired
    private ContactTypeService contactTypeService;

    @Autowired
    private TagTypeService tagTypeService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private ReportClient reportClient;
    @Autowired
    private FilePicService filePicService;

    @Value("${spring.houseService.houseObjtypeId}")
    private int houseObjtypeId;

    //基础房源信息********************************************************************************************************
    @PostMapping("/postinfo")
    public Result<Map<String ,Object>> postHouse(@RequestBody HouseinfoVo houseinfoVo ,
                                     HttpServletRequest request){
        int userId = Integer.parseInt(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        if( houseinfoVo.getCountry() == null ){

            return new Result<>(null,Result.FAIL,"The necessary information is incomplete.There is no country!");
        }
        if(  houseinfoVo.getProvince() == null ){
            return new Result<>(null,Result.FAIL,"The necessary information is incomplete.There is no province!");
        }
        if( houseinfoVo.getCity() == null ){

            return new Result<>(null,Result.FAIL,"The necessary information is incomplete.There is no city!");
        }
        if(houseinfoVo.getAddress() != null && houseinfoVo.getAddress().length()>200)
            return new Result<>(null,Result.FAIL,"The address is too long!");

        if(houseinfoVo.getTitle() != null && houseinfoVo.getTitle().length()>200)
            return new Result<>(null, Result.FAIL, "The title is too long!");
        if(houseinfoVo.getDescription() != null && houseinfoVo.getDescription().length()>500)
            return new Result<>(null, Result.FAIL, "The description is too long!");
        houseinfoVo.setUserId(userId);
        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = postHouseService.addpostHouseInfo(houseinfoVo);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("A successful submission")) msg = "PARAMETER ERROR!";
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }

    @PostMapping("/postHouse")
    public Result<Map<String ,Object>> postHouse(@RequestBody Map<String,Object> map,
                                                 HttpServletResponse response, HttpServletRequest request){

        String s = JSON.toJSONString(map.get("Houseinfo"));
        HouseinfoVo houseinfoVo = JSON.parseObject(s,HouseinfoVo.class);
        postHouse(houseinfoVo,request);

        s = JSON.toJSONString(map.get("ContactList"));
        List<ContactVo>  mapContact = JSON.parseArray(s,ContactVo.class);
        Map<String , List<ContactVo> > ma = new HashMap<>();
        ma.put("date",mapContact);
        postconnect(ma);

        s = JSON.toJSONString(map.get("TegList"));
        List<TagVo>  mapTag = JSON.parseArray(s,TagVo.class);
        Map<String , List<TagVo> > ma1 = new HashMap<>();
        ma1.put("date",mapTag);
        posttag(ma1);

        return new Result<>(Result.SUCCESS,"A successful submission");
    }

    @PostMapping("/uploadHousePic")
    public Result<Map<String ,Object>> uploadHousePic(@RequestPart(value = "files", required = true) MultipartFile file ,
                                                      @RequestPart(value = "Fileinfo") FileName fileName,
                                                  HttpServletResponse response, HttpServletRequest request)  {

        UploadPicUtil uploadPicUtil = new UploadPicUtil();
        String filepath = "";
        try{
            filepath = uploadPicUtil.uploadFile(file);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(null,Result.FAIL,"Image uploading failed");
        }

        Map<String,Object> dateMap =new HashMap<>();
        fileName.setFilePath(filepath);
        if (!filepath.equals(""))
            dateMap = filePicService.uploadHousePic(fileName);

        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Succeeded in modifying data")) msg = "PARAMETER ERROR!";
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }

    @PostMapping("/updateinfo")
    public Result<Map<String ,Object>> updateHouse(@RequestBody HouseinfoVo houseinfoVo ,
                                                 HttpServletResponse response, HttpServletRequest request){

        if(houseinfoVo.getAddress() != null && houseinfoVo.getAddress().length()>200)
            return new Result<>(null,Result.FAIL,"The address is too long!");

        if(houseinfoVo.getTitle() != null && houseinfoVo.getTitle().length()>200)
            return new Result<>(null,Result.FAIL,"The title is too long!");
        if(houseinfoVo.getDescription() != null && houseinfoVo.getDescription().length()>500)
            return new Result<>(null,Result.FAIL,"The description is too long!");

        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = postHouseService.updateHouseInfo(houseinfoVo);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Succeeded in modifying data")) msg = "PARAMETER ERROR!";
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }

    @PostMapping("/deleteinfo")
    public Result<Map<String ,Object>> deleteHouse(@RequestBody Map<String, Object> ma,
                                                   HttpServletResponse response, HttpServletRequest request){

        int houseid = (int)ma.get("houseId");
        Map<String,Object> dateMap = postHouseService.deleteHouseInfo(houseid);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Delete the success!")) msg = "PARAMETER ERROR!";
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }
    @PostMapping("/housedetail")
    public Result<Map<String,Object>>  Housedetial(@RequestBody Map<String, Object> ma){

        int houseid = (int)ma.get("houseId");
        Map<String,Object> dateMap = postHouseService.houseById(houseid);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/selectHouse")
    public Result<Map<String,Object>>  selectHouse(@RequestBody Map<String, Object> selectcondiction,
                                                    HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> dateMap = postHouseService.selcetHouse(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }
    @PostMapping("/selectHouseAdmin")
    public Result<Map<String,Object>>  selectHouseForAdmin(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> dateMap = postHouseService.selectBycondition(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }

//*contact***************************************************************************************************************************

    @PostMapping("/postcontact")
    public Result<Map<String , Object>> postconnect(@RequestBody Map<String , List<ContactVo> > ma){
        List<ContactVo> list = ma.get("date");
        System.out.println(list);
        if(list.size()==0){
//            ResponseUtil.out(response,new Result(null,Result.FAIL,"至少有一个联系方式"));
            return new Result<>(null,Result.FAIL,"At least have one contact information!");
        }

        Map<String,Object> dateMap = postContactService.addPostContact(list);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);

    }

    @PostMapping("/updatecontact")
    public Result<Map<String , Object>> updateconnect(@RequestBody ContactVo contactVo,
                                                    HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postContactService.updateContact(contactVo);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);

    }

    @PostMapping("/deletecontact")
    public Result<Map<String , Object>> deleteconnect(@RequestBody Map<String, Integer> ma,
                                                    HttpServletResponse response, HttpServletRequest request){
        int contactId = ma.get("contactId");

        Map<String ,Object> dateMap = postContactService.deleteContact(contactId);

        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }
    @PostMapping("/selectcontact")
    public Result<Map<String,Object>>  selectContact(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postContactService.selectContact(selectcondiction);
        String msg = "Success!";
        if((int)dateMap.get("number")==0){
            msg = "No query matches the conditions!";
            return new Result<>(dateMap,Result.FAIL,msg);
        }
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }
    @PostMapping("/selectcontactAdmin")
    public Result<Map<String,Object>>  selectContactAll(@RequestBody Map<String, Object> selectcondiction,
                                                     HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postContactService.selectContactAll(selectcondiction);
        String msg = "Success!";
        if((int)dateMap.get("number")==0){
            msg = "No query matches the conditions!";
            return new Result<>(dateMap,Result.FAIL,msg);
        }
        return new Result<>(dateMap, Result.SUCCESS,msg);
    }

//    **************************************************************************************************************
@PostMapping("/postcontacttype")
public Result<Map<String , Object>> postconnecttype(@RequestBody ContactTypeVo contactTypeVo,
                                                HttpServletResponse response, HttpServletRequest request){

    Map<String,Object> dateMap = contactTypeService.addContactType(contactTypeVo);

    String msg = (String) dateMap.get("msg");
    dateMap.remove("msg");
    if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
    return new Result<>(null, Result.SUCCESS,msg);

}

    @PostMapping("/updatecontacttype")
    public Result<Map<String , Object>> updateconnecttype(@RequestBody ContactTypeVo contactTypeVo,
                                                      HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = contactTypeService.updateContactType(contactTypeVo);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);

    }

    @PostMapping("/deletecontacttype")
    public Result<Map<String , Object>> deleteconnecttype(@RequestBody Map<String, Integer> ma,
                                                      HttpServletResponse response, HttpServletRequest request){
        int contactTypeId = ma.get("typeId");
        Map<String ,Object> dateMap = contactTypeService.deleteContactType(contactTypeId);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }

    @PostMapping("/selectcontacttype")
    public Result<Map<String,Object>>  selectContacttype(@RequestBody Map<String, Object> selectcondiction,
                                                     HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = contactTypeService.selectContactType(selectcondiction,false);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/selectcontacttypeAdmin")
    public Result<Map<String,Object>>  selectContacttypeAdmin(@RequestBody Map<String, Object> selectcondiction,
                                                         HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = contactTypeService.selectContactType(selectcondiction,true);
        return new Result<>(dateMap, Result.SUCCESS);
    }



//***********tag**********************************************************************************************************
    @PostMapping("/posttag")
    public Result<Map<String , Object>> posttag(@RequestBody Map<String , List<TagVo> > ma){

        List<TagVo> list = ma.get("date");
        System.out.println(list);
//        System.out.println(list.get(0));

        Map<String,Object> dateMap = postTagService.addPostTag(list);

        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }

    @PostMapping("/deletetag")
    public Result<Map<String , Object>> deletetag(@RequestBody Map<String, Integer> ma,
                                                      HttpServletResponse response, HttpServletRequest request){
        int TagId = ma.get("TagId");

        Map<String ,Object> dateMap = postTagService.deleteTag(TagId);

        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }

    @PostMapping("/selectTag")
    public Result<Map<String,Object>>  selectTag(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postTagService.selectTag(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    //***********************************************************************************************************

    @PostMapping("/posttagtype")
    public Result<Map<String , Object>> posttagtype(@RequestBody Map<String , List<TagTypeVo> > ma,
                                                HttpServletResponse response, HttpServletRequest request){
        List<TagTypeVo> list = ma.get("date");
        Map<String,Object> dateMap = tagTypeService.addTagType(list);

        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }

    @PostMapping("/deletetagtype")
    public Result<Map<String , Object>> deletetagtype(@RequestBody Map<String, Integer> ma,
                                                  HttpServletResponse response, HttpServletRequest request){
        int TagTypeId = ma.get("typeId");

        Map<String ,Object> dateMap = tagTypeService.deleteTagType(TagTypeId);
        String msg = (String) dateMap.get("msg");
        dateMap.remove("msg");
        if(! msg.equals("Success!")) msg = "PARAMETER ERROR!";
        return new Result<>(null, Result.SUCCESS,msg);
    }

    @PostMapping("/selecttagtype")
    public Result<Map<String,Object>>  selecttagtype(@RequestBody Map<String, Object> selectcondiction,
                                                 HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = tagTypeService.selectTagType(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }




////        test

//
//    @Value("${pattern.dateformat}")
//    private String dateformat;
//
//
//    @GetMapping("now")
//    public String now(){
//        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
//    }

    @PostMapping("/report")
    public Result<Map<String, Object>> report(@RequestBody Map<String, Object> reportForm, HttpServletResponse response, HttpServletRequest request){
        Integer postId = (Integer) reportForm.get("postId");
        if(postId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        String reason = (String) reportForm.get("reason");
        if(reason == null || reason.isEmpty()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报理由为空"));
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));

        Map<String, Object> form = FormGeneration.generateReportForm(houseObjtypeId, Long.valueOf(postId.toString()), userId, reason, null, null);

        Result<Map<String, Object>> result = reportClient.addReport(form);
        int code = result.getCode();
        String msg = result.getMsg();
        return new Result<>(null, code, msg);
    }

}
