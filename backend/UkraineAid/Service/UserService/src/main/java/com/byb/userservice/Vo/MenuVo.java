package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;

@Data
public class MenuVo {

    private Integer menuId;

    private String menuName;

    private String url;

    private Date createTime;

}
