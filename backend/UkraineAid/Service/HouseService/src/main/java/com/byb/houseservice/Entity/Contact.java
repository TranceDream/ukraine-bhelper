package com.byb.houseservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.sql.Date;

/**
 * @author simba@onlying.cn
 * @date 2022/5/4 1:03
 */

@TableName("UAHouse.Contact")
@Accessors(chain = true)
@Data
public class Contact {

    @TableId(value = "contactId",type = IdType.AUTO)
    private int contactId;

    @TableField("houseId")
    private int houseId;

    @TableField("createTime")
    private Date createTime;

    @TableField("deleteMask")
    private String deleteMask;

    @TableField("content")
    private String content;

    @TableField("typeId")
    private int typeId;




}
