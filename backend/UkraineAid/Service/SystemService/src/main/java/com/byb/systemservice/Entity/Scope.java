package com.byb.systemservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UkraineAssistance.DICT_SCOPE")
@Accessors(chain = true)
@Data
public class Scope {

    @TableId(value = "SCOPE_ID", type = IdType.AUTO)
    private int scopeId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("SCOPE")
    private String scope;

}
