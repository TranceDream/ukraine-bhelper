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

    @Value("${spring.newsService.newsObjtypeId}")
    private int newsObjtypeId;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/addarticle")
    public Result<Map<String ,Object>> postHouse(@RequestBody ArticleVo articleVo,
                                                 HttpServletResponse response, HttpServletRequest request){

        if( articleVo.getContent() == null || articleVo.getAuthor() == null || articleVo.getTitle() == null ){
//            ResponseUtil.out(response ,new Result(null,Result.FAIL,"必要信息不全"));
            return new Result<>(null,Result.FAIL,"文章必要信息不全");
        }

        System.out.println(articleVo);
        Map<String,Object> dateMap = articleService.addArticle(articleVo);

        return new Result<>(dateMap, Result.SUCCESS);
    }

    @PostMapping("/updatearticle")
    public Result<Map<String ,Object>> updateHouse(@RequestBody ArticleVo articleVo ,
                                                   HttpServletResponse response, HttpServletRequest request){

//        System.out.println(houseinfoVo);
        Map<String,Object> dateMap = articleService.updateArticle(articleVo);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/deletearticle")
    public Result<Map<String ,Object>> deleteHouse(@RequestBody Map<String, Integer> ma,
                                                   HttpServletResponse response, HttpServletRequest request){

        int articleid = ma.get("article");
        Map<String,Object> dateMap = articleService.deleteArticle(articleid);

        return new Result<>(dateMap, Result.SUCCESS);

    }
    @PostMapping("/selectarticle")
    public Result<Map<String,Object>>  selectHouse(@RequestBody Map<String, Object> selectcondiction,
                                                   HttpServletResponse response, HttpServletRequest request){

        Map<String,Object> dateMap = articleService.selcetHouse(selectcondiction);
        return new Result<>(dateMap, Result.SUCCESS);
    }

    //图片上传问题
    @PostMapping("/fileupload")
    @ResponseBody
    public Result<Map<String,Object>> fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        //上传路径保存设置

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "ks-"+ UUID.randomUUID().toString().replaceAll("-", "");
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(new File(realPath +"/"+ filename));

        //给editormd进行回调
        Map<String,Object> res= new HashMap<>();
        res.put("url","/upload/"+month+"/"+ filename);
        res.put("success", 1);
        res.put("message", "upload success!");

        return new Result<> (res, Result.SUCCESS);
    }

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

        Map<String, Object> form = FormGeneration.generateReportForm(newsObjtypeId, postId, userId, reason, null, null);

        reportClient.addReport(form);
        return new Result<>(null, Result.SUCCESS, "举报成功，等待管理员审核");
    }

    @PostMapping("/doAudit")
    public Result<Map<String, Object>> doAudit(@RequestBody Map<String, Object> newsForm, HttpServletResponse response, HttpServletRequest request){

        Long newsId = (Long) newsForm.get("newsId");
        if(newsId == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Integer oper = (Integer) newsForm.get("oper");
        if(oper == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核操作为空"));
        }

        Integer status = (Integer) newsForm.get("status");
        if(status == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核状态为空"));
        }

        String message = (String) newsForm.get("message");

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));

        Map<String, Object> auditForm = FormGeneration.generateAuditForm(newsObjtypeId, newsId, userId, oper, message, null, null);

        Article article = articleService.getById(newsId);
        article.setStatus(status);
        articleService.updateById(article);

        auditClient.addAudit(auditForm);
        return new Result<>(null, Result.SUCCESS, "审核成功");

    }


}
