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
@TableName("UAHouse.TAG")
@Accessors(chain = true)
@Data
public class Tag {

    @TableId(value = "TAG_ID",type = IdType.AUTO)
    private int tagId;

    @TableField("HOUSE_ID")
    private int houseId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMask;

    @TableField("TYPE_ID")
    private int typeId;

}
