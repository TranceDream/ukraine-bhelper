package com.byb.auditservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_AUDIT_OPER")
@Accessors(chain = true)
@Data
public class AuditOper {

    @TableId(value = "OPER_ID", type = IdType.AUTO)
    private int operId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("OPER")
    private String oper;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
