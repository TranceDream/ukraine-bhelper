package com.byb.newsservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.newsservice.Dao.ArticleMapper;
import com.byb.newsservice.Entity.Article;
import com.byb.newsservice.Service.ArticleService;
import com.byb.newsservice.Vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:54
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Override
    public Map<String, Object> addArticle(ArticleVo articleVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            Article article = new Article();
            BeanUtils.copyProperties(articleVo,article);
            baseMapper.insert(article);

            result.put("data",article.getArticleId());
            result.put("msg","提交文章成功");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","提交文章失败");
        }

        return result;
    }

    @Override
    public Map<String, Object> updateArticle(ArticleVo articleVo) {
        Map<String, Object> result = new HashMap<>();
        try{
            Article article = new Article();
            BeanUtils.copyProperties(articleVo,article);
            baseMapper.updateById(article);

            result.put("data",article.getArticleId());
            result.put("msg","修改文章成功");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","修改文章失败");
        }

        return result;
    }

    @Override
    public Map<String, Object> deleteArticle(int articleid) {
        Map<String, Object> result = new HashMap<>();
        try{
            Article article = new Article();
            article.setArticleId(articleid);
            article.setDeleteMark("YES");

            baseMapper.updateById(article);

            result.put("data",article.getArticleId());
            result.put("msg","删除文章成功");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","删除文章失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> selcetArticle(Map<String, Object> selectCondition) {

        int pageSize = (int) (selectCondition.get("pageSize")==null? 10 : selectCondition.get("pageSize"));
        int current = (int) (selectCondition.get("current")==null? 1 : selectCondition.get("current"));
        Page<Article> ArticlePage = new Page<>(current,pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (selectCondition.containsKey("author")){
            Long author = (Long) selectCondition.get("author");
            queryWrapper.eq("author",author);
        }
        if (selectCondition.containsKey("articleId")){
            int articleId = (int) selectCondition.get("articleId");
            queryWrapper.eq("articleId",articleId);
        }
        if (selectCondition.containsKey("createTimeMin")){
            Date createTimeMin = (Date) selectCondition.get("createTimeMin");
            queryWrapper.ge("createTimeMin",createTimeMin);
        }
        if (selectCondition.containsKey("createTimeMax")){
            Date createTimeMax = (Date) selectCondition.get("createTimeMax");
            queryWrapper.le("createTimeMax",createTimeMax);
        }
        if (selectCondition.containsKey("deleteMark")){
            String deleteMark = (String) selectCondition.get("deleteMark");
            queryWrapper.eq("deleteMark",deleteMark);
        }
        else{
            queryWrapper.eq("deleteMark","NO");
        }
        if (selectCondition.containsKey("status")){
            int status = (int) selectCondition.get("status");
            queryWrapper.eq("status",status);
        }
        if (selectCondition.containsKey("groupId")){
            int groupId = (int) selectCondition.get("groupId");
            System.out.println(groupId);
            queryWrapper.eq("groupId",groupId);
        }


        String scope = (String) selectCondition.get("scope");
        System.out.println(scope);
        scope = scope.substring(1,scope.length()-1);
        List<String> groupIds = Arrays.asList(scope.split(","));
        queryWrapper.in("groupId",groupIds);

        Page<Article> page = this.page(ArticlePage,queryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("articles",page.getRecords());
        result.put("count",baseMapper.selectCount(queryWrapper));
        return result;
    }

    @Override
    public Map<String, Object> selcetArticleForC(Map<String, Object> selectCondition) {

        int pageSize;
        if (selectCondition.containsKey("pageSize"))
            pageSize = (int) selectCondition.get("pageSize");
        else pageSize = 10;
        int current;
        if (selectCondition.containsKey("current"))
            current = (int) selectCondition.get("current");
        else current = 1;

        Page<Article> ArticlePage = new Page<>(current,pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (selectCondition.containsKey("author")){
            Long author = (Long) selectCondition.get("author");
            queryWrapper.eq("author",author);
        }
        if (selectCondition.containsKey("articleId")){
            int articleId = (int) selectCondition.get("articleId");
            queryWrapper.eq("articleId",articleId);
        }
        if (selectCondition.containsKey("createTimeMin")){
            Date createTimeMin = (Date) selectCondition.get("createTimeMin");
            queryWrapper.ge("createTimeMin",createTimeMin);
        }
        if (selectCondition.containsKey("createTimeMax")){
            Date createTimeMax = (Date) selectCondition.get("createTimeMax");
            queryWrapper.le("createTimeMax",createTimeMax);
        }
        if (selectCondition.containsKey("deleteMark")){
            String deleteMark = (String) selectCondition.get("deleteMark");
            queryWrapper.eq("deleteMark",deleteMark);
        }
        else{
            queryWrapper.eq("deleteMark","NO");
        }
//        if (selectCondition.containsKey("status")){
//            int status = (int) selectCondition.get("status");
            queryWrapper.eq("status",2);
//        }
        if (selectCondition.containsKey("groupId")){
            int groupId = (int) selectCondition.get("groupId");
            queryWrapper.eq("groupId",groupId);
        }
        Page<Article> page = this.page(ArticlePage,queryWrapper);
        Map<String, Object> result = new HashMap<>();
        result.put("articles",page.getRecords());
        result.put("count",baseMapper.selectCount(queryWrapper));
        return result;
    }
}
