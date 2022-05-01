package com.byb.auditservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_STATUS")
@Accessors(chain = true)
@Data
public class Status {

    @TableId(value = "STATUS_ID", type = IdType.AUTO)
    private int statusId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("STATUS")
    private String status;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
