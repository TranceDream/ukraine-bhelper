package com.byb.systemservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@TableName("UA.MESSAGE")
@Accessors(chain = true)
@Data
public class Message {

    @TableId(value = "MESSAGE_ID", type = IdType.AUTO)
    private Long messageId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("CONTENT")
    private String content;

    @TableField("SPECIFIC_USERS")
    private String specificUsers;

    @TableField("TITLE")
    private String title;

}
