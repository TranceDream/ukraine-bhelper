package com.byb.newsservice.Controller;

//import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.newsservice.Entity.Article;
import com.byb.newsservice.Service.ArticleService;
import com.byb.newsservice.Vo.ArticleVo;
import com.byb.openfeign.Client.AuditClient;
import com.byb.openfeign.Client.ReportClient;
import com.byb.openfeign.Client.UserClient;
import com.byb.openfeign.Form.FormGeneration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/5 0:03
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/news")
@Component
@Configuration
public class NewsController {

    @PostMapping("/test")
    public Result<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "welcome to news module");
        return new Result<>(map, Result.SUCCESS);
    }

    @Autowired
    private ReportClient reportClient;

    @Autowired
    private AuditClient auditClient;

    @Autowired
    private UserClient userClient;

    @Value("${spring.newsService.newsObjtypeId}")
    private int newsObjtypeId;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public Result<Map<String ,Object>> postHouse(@RequestBody ArticleVo articleVo,
                                                 HttpServletResponse response, HttpServletRequest request){

        if( articleVo.getContent() == null || articleVo.getTitle() == null ){
//            ResponseUtil.out(response ,new Result(null,Result.FAIL,"??????????????????"));
            return new Result<>(null,Result.FAIL,"????????????????????????");
        }
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", 10002);
        Result<Map<String, Object>> result = userClient.getOneGroup(params);
        Map<String, Object> groupMap = result.getData();
        if(groupMap == null || groupMap.get("groupId") == null){
            return new Result<>(null,Result.FAIL,"???????????????????????????");
        }
        Integer groupId = (Integer) groupMap.get("groupId");
        if(groupId == null){
            return new Result<>(null,Result.FAIL,"???????????????????????????");
        }
        articleVo.setAuthor(userId);
        articleVo.setGroupId(groupId);
        System.out.println(articleVo);
        Map<String,Object> dateMap = articleService.addArticle(articleVo);

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/updateArticle")
    public Result<Map<String ,Object>> updateHouse(@RequestBody ArticleVo articleVo ,
                                                   HttpServletResponse response, HttpServletRequest request){

//        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = articleService.updateArticle(articleVo);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/deleteArticle")
    public Result<Map<String ,Object>> deleteHouse(@RequestBody Map<String, Integer> ma,
                                                   HttpServletResponse response, HttpServletRequest request){

        int articleid = ma.get("article");
        Map<String,Object> dateMap = articleService.deleteArticle(articleid);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/selectArticle")
    public Result<Map<String,Object>>  selectHouse(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        String scope = userClient.getChildGroupsSql(userId);
        selectcondiction.put("scope", scope);
        Map<String,Object> dateMap = articleService.selcetArticle(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }
    @PostMapping("/selectArticleForC")
    public Result<Map<String,Object>>  selectHouseForC(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){
//        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
//        String scope = userClient.getChildGroupsSql(userId);
//        selectcondiction.put("scope", scope);
        Map<String,Object> dateMap = articleService.selcetArticleForC(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/selectYourArticle")
    public Result<Map<String, Object>> selectYourArticle(@RequestBody Map<String, Object> selectcondiction,
                                                         HttpServletResponse response, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        selectcondiction.put("author", userId);
        Map<String,Object> dateMap = articleService.selcetArticle(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    //??????????????????
    @PostMapping("/fileupload")
    @ResponseBody
    public Result<Map<String,Object>> fileUpload(@RequestParam(value = "newsPic") MultipartFile file, HttpServletRequest request) throws IOException {
        //????????????????????????

        //??????SpringBoot????????????????????????System.getProperty("user.dir")
        String path ="/Ukother/Ukpic/NewPic/";

        //???????????????????????????
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"mouth";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //??????????????????
        System.out.println("???????????????????????????"+realPath);

        //???????????????????????????????????????uuid;

        String filename = UUID.randomUUID().toString().replaceAll("-", "")+file.getOriginalFilename();
        //??????CommonsMultipartFile????????????????????????????????????????????????
        file.transferTo(new File(realPath +"/"+ filename));

        //???editormd????????????
        Map<String,Object> res= new HashMap<>();
        res.put("url","image/newsimage/"+month+"/"+ filename);
        res.put("success", 1);
        res.put("message", "upload success!");

        return new Result<> (res, Result.SUCCESS);
    }

    @PostMapping("/report")
    public Result<Map<String, Object>> report(@RequestBody Map<String, Object> reportForm, HttpServletResponse response, HttpServletRequest request){
        Integer articleId = (Integer) reportForm.get("articleId");
        if(articleId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        String reason = (String) reportForm.get("reason");
        if(reason == null || reason.isEmpty()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "??????????????????"));
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));

        Map<String, Object> form = FormGeneration.generateReportForm(newsObjtypeId, Long.valueOf(articleId.toString()), userId, reason, null, null);

        reportClient.addReport(form);
        return new Result<>(null, Result.SUCCESS, "????????????????????????????????????");
    }

    @PostMapping("/doAudit")
    public Result<Map<String, Object>> doAudit(@RequestBody Map<String, Object> newsForm, HttpServletResponse response, HttpServletRequest request){

        Integer newsId = (Integer) newsForm.get("articleId");
        if(newsId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        int oper = 0;
        String msg = "";
        int status = 1;
        if(newsForm.get("oper") == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "??????????????????"));
        }
        if(newsForm.get("oper").toString().equals("??????")){
            oper = 10002;
            msg = "??????????????????";
            status = 2;
        }else if(newsForm.get("oper").toString().equals("??????")){
            oper = 10003;
            msg = "??????????????????";
            status = 3;
        }

        String message = (String) newsForm.get("message");
        if(message == null){
            message = msg;
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));

        Map<String, Object> auditForm = FormGeneration.generateAuditForm(newsObjtypeId, Long.valueOf(newsId.toString()), userId, oper, message, null, null);

        Article article = articleService.getById(newsId);
        article.setStatus(status);
        articleService.updateById(article);

        auditClient.addAudit(auditForm);
        return new Result<>(null, Result.SUCCESS, "????????????");

    }

    @PostMapping("/getAuditLog")
    public Result<List<Object>> getAuditLog(@RequestBody Map<String, Object> params, HttpServletResponse response){
        Integer articleId = (Integer) params.get("articleId");
        if(articleId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Map<String, Object> auditForm = new HashMap<>();
        auditForm.put("objtypeId", newsObjtypeId);
        auditForm.put("objId", articleId);
        Result<List<Object>> auditResult = auditClient.getReportList(auditForm);
        if(auditResult == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "????????????"));
        }

        return auditResult;
    }

    @PostMapping("/getNewsGroup")
    public Result<Map<Integer, String>> getNewsGroup(){
        Result<Map<Integer, String>> result = userClient.getChildGroup(10001);
        return result;
    }

}
