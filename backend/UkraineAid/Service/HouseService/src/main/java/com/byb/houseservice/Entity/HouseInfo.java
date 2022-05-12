package com.byb.houseservice.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

//import java.util.Date;

/**
 * @author zjt
 * @date 2022/5/4 0:25
 */
@TableName("UAHouse.HouseInfo")
@Accessors(chain = true)
@Data
//@NoArgsConstructor
//@RequiredArgsConstructor
public class HouseInfo {

    @TableId(value = "houseId",type = IdType.AUTO)
    private int houseId;

    @TableField(value = "userId")
    private int userId;

    @TableField("createTime")
    private Date createTime;

    @TableField("deleteMark")
    private String deleteMark;

    @TableField("country")
    private String country;

    @TableField("province")
    private String province;

    @TableField("city")
    private String city;

    @TableField("address")
    private String address;

    @TableField("guests")
    private int guests;

    @TableField("pets")
    private String pets;

    @TableField("duration")
    private int duration;

    @TableField("description")
    private String description ;

    @TableField("title")
    private String title;

    @TableField("active")
    private String active;
}
