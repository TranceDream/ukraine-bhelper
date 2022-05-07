package com.byb.userservice.Vo;

import lombok.Data;

import java.util.List;

@Data
public class ModuleVo {

    private Integer moduleId;

    private String title;

    private List<ModuleVo> childs;

    private String key;

    private String icon;

}
