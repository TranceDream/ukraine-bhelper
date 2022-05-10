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
 * @date 2022/5/4 1:13
 */
@TableName("UAHouse.tag")
@Accessors(chain = true)
@Data
public class Tag {

    @TableId(value = "tagId",type = IdType.AUTO)
    private int tagId;

    @TableField("houseId")
    private int houseId;

    @TableField("createTime")
    private Date createTime;

    @TableField("deleteMark")
    private String deleteMark;

    @TableField("typeId")
    private int typeId;

}
