package com.byb.userservice.Vo;

import lombok.Data;

import java.util.List;

@Data
public class ModuleVo {

    private Integer moduleId;

    private String moduleName;

    private List<ModuleVo> childs;

    private String url;

}
