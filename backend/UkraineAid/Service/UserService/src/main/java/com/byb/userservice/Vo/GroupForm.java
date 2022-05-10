package com.byb.userservice.Vo;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupForm {

    private int groupId;

    private Date createTime;

    private String groupName;

    private int parentId;

    private String deleteMark;

    private List<GroupForm> childs = new ArrayList<>();

}
