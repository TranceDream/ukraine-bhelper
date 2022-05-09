package com.byb.userservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.DICT_GROUP")
@Accessors(chain = true)
@Data
public class Group {

    @TableId(value = "GROUP_ID", type = IdType.AUTO)
    private int groupId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("GROUP_NAME")
    private String groupName;

    @TableField("PARENT_ID")
    private int parentId;

    @TableField("DELETE_MARK")
    private String deleteMark;

}
