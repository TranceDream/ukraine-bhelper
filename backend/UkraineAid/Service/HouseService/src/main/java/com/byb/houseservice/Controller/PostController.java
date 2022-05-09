package com.byb.houseservice.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.houseservice.Entity.HouseInfo;
import com.byb.houseservice.Service.*;
import com.byb.houseservice.Vo.*;
import com.byb.openfeign.Client.ReportClient;
import com.byb.openfeign.Form.FormGeneration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Value("${spring.houseService.houseObjtypeId}")
    private int houseObjtypeId;

    //基础房源信息********************************************************************************************************
    @PostMapping("/postinfo")
    public Result<Map<String ,Object>> postHouse(@RequestBody HouseinfoVo houseinfoVo ){
        if( houseinfoVo.getCountry() == null || houseinfoVo.getProvince() == null || houseinfoVo.getCity() == null ){
//            ResponseUtil.out(response ,new Result(null,Result.FAIL,"必要信息不全"));
            return new Result<>(null,Result.FAIL,"必要信息不全");
        }
        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = postHouseService.addpostHouseInfo(houseinfoVo);

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/updateinfo")
    public Result<Map<String ,Object>> updateHouse(@RequestBody HouseinfoVo houseinfoVo ,
                                                 HttpServletResponse response, HttpServletRequest request){

        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = postHouseService.updateHouseInfo(houseinfoVo);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/deleteinfo")
    public Result<Map<String ,Object>> deleteHouse(@RequestBody Map<String, Integer> ma,
                                                   HttpServletResponse response, HttpServletRequest request){

        int houseid = ma.get("houseId");
        Map<String,Object> dateMap = postHouseService.deleteHouseInfo(houseid);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/selectHouse")
    public Result<Map<String,Object>>  selectHouse(@RequestBody Map<String, Object> selectcondiction,
                                                    HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postHouseService.selcetHouse(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }



//*contact***************************************************************************************************************************

    @PostMapping("/postcontact")
    public Result<Map<String , Object>> postconnect(@RequestBody Map<String , List<ContactVo> > ma,
                                                    HttpServletResponse response, HttpServletRequest request){
        List<ContactVo> list = ma.get("date");
        if(list.size()==0){
//            ResponseUtil.out(response,new Result(null,Result.FAIL,"至少有一个联系方式"));
            return new Result<>(null,Result.FAIL,"至少有一个联系方式");
        }

        Map<String,Object> dateMap = postContactService.addPostContact(list);

        return new Result<>(dateMap, Result.SUCCESS);

    }

    @PostMapping("/updatecontact")
    public Result<Map<String , Object>> updateconnect(@RequestBody ContactVo contactVo,
                                                    HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postContactService.updateContact(contactVo);
        return new Result<>(dateMap, Result.SUCCESS);

    }

    @PostMapping("/deletecontact")
    public Result<Map<String , Object>> deleteconnect(@RequestBody Map<String, Integer> ma,
                                                    HttpServletResponse response, HttpServletRequest request){
        int contactId = ma.get("contactId");

        Map<String ,Object> dateMap = postContactService.deleteContact(contactId);

        return new Result<>(dateMap, Result.SUCCESS);
    }
    @PostMapping("/selectcontact")
    public Result<Map<String,Object>>  selectContact(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postContactService.selectContact(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }
//    **************************************************************************************************************
@PostMapping("/postcontacttype")
public Result<Map<String , Object>> postconnecttype(@RequestBody ContactTypeVo contactTypeVo,
                                                HttpServletResponse response, HttpServletRequest request){


    Map<String,Object> dateMap = contactTypeService.addContactType(contactTypeVo);

    return new Result<>(dateMap, Result.SUCCESS);

}

    @PostMapping("/updatecontacttype")
    public Result<Map<String , Object>> updateconnecttype(@RequestBody ContactTypeVo contactTypeVo,
                                                      HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = contactTypeService.updateContactType(contactTypeVo);
        return new Result<>(dateMap, Result.SUCCESS);

    }

    @PostMapping("/deletecontacttype")
    public Result<Map<String , Object>> deleteconnecttype(@RequestBody Map<String, Integer> ma,
                                                      HttpServletResponse response, HttpServletRequest request){
        int contactTypeId = ma.get("typeId");
        Map<String ,Object> dateMap = contactTypeService.deleteContactType(contactTypeId);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/selectcontacttype")
    public Result<Map<String,Object>>  selectContacttype(@RequestBody Map<String, Object> selectcondiction,
                                                     HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = contactTypeService.selectContactType(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }



//***********tag**********************************************************************************************************
    @PostMapping("/posttag")
    public Result<Map<String , Object>> posttag(@RequestBody Map<String , List<TagVo> > ma,
                                                    HttpServletResponse response, HttpServletRequest request){
        List<TagVo> list = ma.get("date");

        Map<String,Object> dateMap = postTagService.addPostTag(list);

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/deletetag")
    public Result<Map<String , Object>> deletetag(@RequestBody Map<String, Integer> ma,
                                                      HttpServletResponse response, HttpServletRequest request){
        int TagId = ma.get("TagId");

        Map<String ,Object> dateMap = postTagService.deleteTag(TagId);

        return new Result<>(dateMap, Result.SUCCESS);
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

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/deletetagtype")
    public Result<Map<String , Object>> deletetagtype(@RequestBody Map<String, Integer> ma,
                                                  HttpServletResponse response, HttpServletRequest request){
        int TagTypeId = ma.get("typeId");

        Map<String ,Object> dateMap = tagTypeService.deleteTagType(TagTypeId);

        return new Result<>(dateMap, Result.SUCCESS);
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
        Long postId = (Long) reportForm.get("postId");
        if(postId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        String reason = (String) reportForm.get("reason");
        if(reason == null || reason.isEmpty()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报理由为空"));
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));

        Map<String, Object> form = FormGeneration.generateReportForm(houseObjtypeId, postId, userId, reason, null, null);

        reportClient.addReport(form);
        return new Result<>(null, Result.SUCCESS, "举报成功，等待管理员审核");
    }

}
