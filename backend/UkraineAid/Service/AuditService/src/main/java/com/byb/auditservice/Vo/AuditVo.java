package com.byb.auditservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class AuditVo {

    private int auditId;

    private Date createTime;

    private Integer objtypeId;

    private Long objId;

    private Long operator;

    private Integer oper;

    private String operation;

    private String message;

    private String deleteMark;

}
