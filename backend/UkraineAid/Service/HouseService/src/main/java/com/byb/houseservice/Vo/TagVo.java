package com.byb.houseservice.Vo;

import lombok.Data;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 14:04
 */
@Data
public class TagVo {

    private int tagId;

    private int houseId;

    private String deleteMark;

    private int typeId;
}
