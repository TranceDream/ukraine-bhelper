package com.byb.newsservice.Vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 23:45
 */

@Data
public class ArticleVo  {

    private String author;

    private String title;

    private String content;

    private int articleid;

    private Date creattime;

    private String deletemark;

    private Integer status;

    private Integer pageNo;

    private Integer pageSize;
}
