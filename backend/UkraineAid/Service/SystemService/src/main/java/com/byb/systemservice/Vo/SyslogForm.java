package com.byb.systemservice.Vo;

import lombok.Data;
import java.sql.Date;

@Data
public class SyslogForm {

    private Long logId;

    private Date createTime;

    private String deleteMark;

    private int objtypeId;

    private Long objId;

    private Long operator;

    private String message;

    private String objtype;

}
