package com.guide.common.mapstruct;

import cn.hutool.core.lang.Assert;
import com.guide.common.mapstruct.entity.User;
import com.guide.common.mapstruct.opi.UserConverter;
import com.guide.common.mapstruct.vo.UserVo;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * MapStruct是一款非常实用Java工具，主要用于解决对象之间的拷贝问题，比如PO/DTO/VO/QueryParam之间的转换问题。区
 * 别于BeanUtils这种通过反射，它通过编译器编译生成常规方法，将可以很大程度上提升效率。
 */
public class MapStructTets
{

    @Test
    public void do2VoTest()
    {
        User user = new User().setId(1L).setUsername("zhangsan").setSex(1).setPassword("abc123")
                .setCreateTime(LocalDateTime.now()).setBirthday(LocalDate.of(1999, 9, 27))
                .setConfig("{\"field1\":\"Test Field1\",\"field2\":500}");

        UserVo userVo = UserConverter.INSTANCE.do2vo(user);

        // asset
        Assert.notNull(userVo);
        Assert.isTrue(userVo.getId().equals(user.getId()));

        // print
        System.out.println(user);
        System.out.println(userVo);
    }

    @Test
    public void vo2DoTest()
    {
        UserVo.UserConfig userConfig = new UserVo.UserConfig();
        userConfig.setField1("Test Field1");
        userConfig.setField2(500);

        UserVo userVo = new UserVo().setId(1L).setUsername("zhangsan").setGender(2).setCreateTime("2020-01-18 15:32:54")
                .setBirthday(LocalDate.of(1999, 9, 27)).setConfig(userConfig);
        User user = UserConverter.INSTANCE.vo2Do(userVo);

        // asset
        Assert.notNull(userVo);
        Assert.isTrue(userVo.getId().equals(user.getId()));

        // print
        System.out.println(user);
        System.out.println(userVo);
    }

}
