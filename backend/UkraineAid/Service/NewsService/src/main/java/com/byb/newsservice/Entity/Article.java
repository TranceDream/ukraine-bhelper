package com.byb.newsservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:36
 */
@TableName("UANews.article")
@Accessors(chain = true)
@Data
public class Article {
    @TableId(value = "articleid",type = IdType.AUTO)
    private int articleid;

    @TableField("creattime")
    private Data creattime;

    @TableField("deletemark")
    private String deletemark;

    @TableField("author")
    private String author;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

}
