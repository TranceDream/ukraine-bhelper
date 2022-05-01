package com.byb.reportservice.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

@Data
public class ReportForm {

    private int reportId;

    private Date createTime;

    private Integer objtypeId;

    private Long defense;

    private String reason;

    private Long prosecution;

    private String handleMark;

    private String deleteMark;

    private Integer pageSize;

    private Integer pageNo;

}
