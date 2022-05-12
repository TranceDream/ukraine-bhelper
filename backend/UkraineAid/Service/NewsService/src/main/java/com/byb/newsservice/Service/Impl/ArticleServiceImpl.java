package com.byb.newsservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.newsservice.Dao.ArticleMapper;
import com.byb.newsservice.Entity.Article;
import com.byb.newsservice.Service.ArticleService;
import com.byb.newsservice.Vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            result.put("data",article.getArticleid());
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

            result.put("data",article.getArticleid());
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
            article.setArticleid(articleid);
            article.setDeletemark("YES");

            baseMapper.updateById(article);

            result.put("data",article.getArticleid());
            result.put("msg","删除文章成功");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","删除文章失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> selcetHouse(Map<String, Object> selectCondition) {
        Map<String, Object> result = new HashMap<>();
        int pageNo = (int)selectCondition.get("pageNo");
        int pageSize = (int) selectCondition.get("pageSize");
        try{
            List<Article> articlesList = baseMapper.selectByMap(selectCondition);
            articlesList = articlesList.subList((pageNo-1)*pageSize,pageNo*pageSize);
            System.out.println(selectCondition);
            result.put("data",articlesList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","失败");
        }
        return result;
    }
}
