package com.byb.newsservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.newsservice.Entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:43
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
