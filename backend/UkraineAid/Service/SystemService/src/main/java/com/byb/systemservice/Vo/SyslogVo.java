package com.byb.systemservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class SyslogVo {

    private Long logId;

    private Date createTime;

    private String deleteMark;

    private Integer objtypeId;

    private Long objId;

    private Long operator;

    private Integer operationId;

    private String message;

    private String objtype;

    private String operation;

}
