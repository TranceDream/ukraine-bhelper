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
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 23:20
 */
@TableName("UAHouse.filename")
@Accessors(chain = true)
@Data
public class FileName {

    @TableId(value = "picId",type = IdType.AUTO)
    private int picId;

    @TableField("createTime")
    private Date createTime;

    @TableField("deleteMark")
    private String deleteMark;

    @TableField("filePath")
    private String filePath;
    @TableField("houseId")
    private int houseId;

}
