package com.byb.security.Entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class User implements Serializable {
    private Long userId;
    private Date createTime;
    private String country;
    private String city;
    private int identityType;
    private String identifier;
    private String credential;
}
