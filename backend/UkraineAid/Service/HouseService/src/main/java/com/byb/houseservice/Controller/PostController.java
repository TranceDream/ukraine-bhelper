package com.byb.houseservice.Controller;

import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.houseservice.Service.PostContactService;
import com.byb.houseservice.Service.PostHouseService;
import com.byb.houseservice.Service.PostTagService;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.HouseinfoVo;
import com.byb.houseservice.Vo.TagVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PostTagService postTagService;

    //基础房源信息********************************************************************************************************
    @PostMapping("/postinfo")
    public Result<Map<String ,Object>> postHouse(@RequestBody HouseinfoVo houseinfoVo ,
                                                 HttpServletResponse response, HttpServletRequest request){
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

        Map<String,Object> dateMap = postContactService.selectContact(selectcondiction)
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
        int TagId = ma.get("contactId");

        Map<String ,Object> dateMap = postTagService.deleteTag(TagId);

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/selectTag")
    public Result<Map<String,Object>>  selectTag(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = postTagService.selectTag(selectcondiction);
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

}
