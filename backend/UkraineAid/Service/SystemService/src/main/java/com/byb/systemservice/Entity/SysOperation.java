package com.byb.systemservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_SYS_OPERATION")
@Accessors(chain = true)
@Data
public class SysOperation {

    @TableId(value = "OPER_ID", type = IdType.AUTO)
    private int operId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("OPER")
    private String oper;

}
