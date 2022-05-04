package com.byb.newsservice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Utils.Result;
import com.byb.newsservice.Service.ArticleService;
import com.byb.newsservice.Vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
public class NewsController {

    @PostMapping("/test")
    public Result<Map<String, Object>> test() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "welcome to house module");
        return new Result<>(map, Result.SUCCESS);
    }

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
    @RequestMapping("/fileupload")
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



}
