package com.byb.houseservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * @author zjt
 * @date 2022/5/4 1:15
 */


@TableName("UAHouse.tagType")
@Accessors(chain = true)
@Data
public class TagType {

    @TableId(value = "typeId",type = IdType.AUTO)
    private int typeId;

    @TableField("createTime")
    private Date createTime;

    @TableField("deleteMark")
    private String deleteMark;

    @TableField("tagName")
    private String tagName;
}
