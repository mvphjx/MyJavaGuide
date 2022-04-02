package com.guide.common.mapstruct.vo;

import lombok.Data;

@Data
public class UserWithAddressVo {

    private String username;
    private Integer sex;
    private String street;
    private Integer zipCode;
    private Integer houseNumber;
    private String description;
}
