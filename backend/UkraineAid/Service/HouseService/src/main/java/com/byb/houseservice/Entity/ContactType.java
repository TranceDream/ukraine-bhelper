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
 * @date 2022/5/4 1:11
 */

@TableName("UAHouse.CONTACT_TYPE")
@Accessors(chain = true)
@Data
public class ContactType {

    @TableId(value = "TYPE_ID",type = IdType.AUTO)
    private int typeId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMask;

    @TableField("CONTACT_NAME")
    private String contentName;


}
