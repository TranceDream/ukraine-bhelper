package com.byb.auditservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.AUDIT")
@Accessors(chain = true)
@Data
public class Audit {

    @TableId(value = "AUDIT_ID", type = IdType.AUTO)
    private int auditId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("OBJTYPE_ID")
    private Integer objtypeId;

    @TableField("OBJ_ID")
    private Long objId;

    @TableField("STATUS")
    private Integer status;

    @TableField("OPERATOR")
    private Long operator;

    @TableField("OPER")
    private Integer oper;

    @TableField("MESSAGE")
    private String message;

    @TableField("DELETE_MARK")
    private String deleteMark;
}
