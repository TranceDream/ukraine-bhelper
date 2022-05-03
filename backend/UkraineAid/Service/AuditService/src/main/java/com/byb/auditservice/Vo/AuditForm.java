package com.byb.auditservice.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class AuditForm {

    private int auditId;

    private Date createTime;

    private Integer objtypeId;

    private Long objId;

    private Integer status;

    private Long operator;

    private Integer oper;

    private String message;

    private String deleteMark;
}
