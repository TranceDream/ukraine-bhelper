package com.byb.systemservice.Vo;

import lombok.Data;
import java.sql.Date;

@Data
public class SyslogForm {

    private Long logId;

    private Date createTime;

    private String deleteMark;

    private Integer objtypeId;

    private Long objId;

    private Long operator;

    private Integer operation;

    private String message;

    private String objtype;

    private Integer pageSize;

    private Integer current;

    private String orderText;

}
