package com.guide.common.design.chain.impl.login.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginInfo
{
    private String type;
    private String token;
    private String userName;
    private String unitcode;
}
