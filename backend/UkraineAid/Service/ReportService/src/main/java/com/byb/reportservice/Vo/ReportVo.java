package com.byb.reportservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class ReportVo {

    private int reportId;

    private Date createTime;

    private Integer objtypeId;

    private Long defense;

    private String reason;

    private Long prosecution;

    private Integer auditStatus;

    private String deleteMark;

    private Integer count;

}
