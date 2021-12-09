package com.guide.common.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserVo extends UserBo
{
    private String password;
    private int sex;
    private String sexText;

    public void fromObject()
    {
        switch (this.getSex())
        {
        case 1:
            this.sexText = "男";
            break;
        case 2:
            this.sexText = "女";
            break;
        default:
            this.sexText = "未知";
        }
    }

    @JsonIgnore
    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
