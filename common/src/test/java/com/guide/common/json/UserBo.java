package com.guide.common.json;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserBo
{
    private Long id;
    private String name;
    private String password;
    private int sex;
    private Timestamp createTime;
}
