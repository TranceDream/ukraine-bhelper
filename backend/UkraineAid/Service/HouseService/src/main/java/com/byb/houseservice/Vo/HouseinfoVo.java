package com.byb.houseservice.Vo;

import lombok.Data;

import java.util.Map;

/**
 * @author zjt
 * @date 2022/5/3 23:05
 */
@Data
public class HouseinfoVo {

    private int userId;

    private int houseId;

    private String country;

    private String province;

    private String deleteMark;

    private String city;

    private String address;

    private int guests;

    private String pets;

    private int duration;

    private String description ;

    private String title;

    private String active;

    private int pageSize;

    private int pageNo;

    private String fileNames;
}
