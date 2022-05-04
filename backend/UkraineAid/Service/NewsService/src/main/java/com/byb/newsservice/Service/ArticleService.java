package com.byb.newsservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.newsservice.Entity.Article;
import com.byb.newsservice.Vo.ArticleVo;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:48
 */
@Repository
public interface ArticleService extends IService<Article> {

    Map<String ,Object> addArticle(ArticleVo articleVo);
    Map<String ,Object> updateArticle(ArticleVo articleVo);
    Map<String ,Object> deleteArticle(int articleid);
    Map<String,Object> selcetHouse(Map<String,Object> selectCondition);

}
