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

@TableName("UAHouse.CONTACT")
@Accessors(chain = true)
@Data
public class Contact {

    @TableId(value = "CONTACT_ID",type = IdType.AUTO)
    private int contactId;

    @TableField("HOUSE_ID")
    private int houseId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMask;

    @TableField("CONTENT")
    private String content;

    @TableField("TYPE_ID")
    private int typeId;




}
