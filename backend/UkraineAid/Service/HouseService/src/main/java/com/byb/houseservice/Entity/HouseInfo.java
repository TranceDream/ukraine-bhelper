package com.byb.houseservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import lombok.Data;

import java.sql.Date;

//import java.util.Date;

/**
 * @author zjt
 * @date 2022/5/4 0:25
 */
@TableName("UAHouse.HOUSEINFO")
@Accessors(chain = true)
@Data
public class HouseInfo {

    @TableId(value = "HOUSE_ID",type = IdType.AUTO)
    private int houseId;

    @TableField(value = "USER_ID")
    private int userid;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("DELETE_MARK")
    private String deleteMark;

    @TableField("COUNTRY")
    private String country;

    @TableField("PROVINCE")
    private String province;

    @TableField("CITY")
    private String city;

    @TableField("ADDRESS")
    private String address;

    @TableField("GUESTS")
    private int guest;

    @TableField("PETS")
    private String pets;

    @TableField("DURATION")
    private int duration;

    @TableField("DESCRIPTION")
    private String description ;

    @TableField("TITLE")
    private String title;

    @TableField("ACTIVE")
    private String active;
}
