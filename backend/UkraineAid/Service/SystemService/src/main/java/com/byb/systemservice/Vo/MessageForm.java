package com.byb.systemservice.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class MessageForm {

    private int messageId;

    private Date createTime;

    private String deleteMark;

    private Integer scope;

    private String scopeDetail;

    private String content;

    private Long specificUsers;

    private String title;

}
