package com.byb.reportservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.REPORT")
@Accessors(chain = true)
@Data
public class Report {

    @TableId(value = "REPORT_ID", type = IdType.AUTO)
    private int reportId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("OBJTYPE_ID")
    private Integer objtypeId;

    @TableField("DEFENSE")
    private Long defense;

    @TableField("REASON")
    private String reason;

    @TableField("PROSECUTION")
    private Long prosecution;

    @TableField("AUDIT_STATUS")
    private Integer auditStatus;

    @TableField("DELETE_MARK")
    private String deleteMark;
}
