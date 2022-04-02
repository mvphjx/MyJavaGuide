package com.guide.common.mapstruct.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User
{
    private Long id;
    private String username;
    // 密码
    private String password;
    // 性别
    private Integer sex;
    // 生日
    private LocalDate birthday;
    // 创建时间
    private LocalDateTime createTime;
    // 其他扩展信息，以JSON格式存储
    private String config;
}
