package com.byb.newsservice.Vo;


import lombok.Data;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:45
 */
@Data
public class ArticleVo {

    private int articleid;

    private Data creattime;

    private String deletemark;

    private String author;

    private String title;

    private String content;
}
