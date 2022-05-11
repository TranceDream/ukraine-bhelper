package com.byb.houseservice.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Date;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 15:49
 */
@Data
public class TagTypeVo {

    private int typeId;

    private Date createTime;

    private String deleteMark;

    private String tagName;
}
