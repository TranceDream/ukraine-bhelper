package com.byb.systemservice.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class MessageForm {

    private Long messageId;

    private Date createTime;

    private String deleteMark;

    private Integer scope;

    private String scopeDetail;

    private String content;

    private List<Long> specificUsers;

    private String title;

    private String[] emails;

}
