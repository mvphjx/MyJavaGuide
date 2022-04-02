package com.guide.common.mapstruct.opi;

import com.alibaba.fastjson.JSON;
import com.guide.common.mapstruct.entity.User;
import com.guide.common.mapstruct.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter
{
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mapping(target = "gender", source = "sex")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserVo do2vo(User var1);

    @Mapping(target = "sex", source = "gender")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    User vo2Do(UserVo var1);


    List<UserVo> do2voList(List<User> userList);

    default List<UserVo.UserConfig> strConfigToListUserConfig(String config)
    {
        return JSON.parseArray(config, UserVo.UserConfig.class);
    }

    default String listUserConfigToStrConfig(List<UserVo.UserConfig> list)
    {
        return JSON.toJSONString(list);
    }


    default UserVo.UserConfig strConfigToUserConfig(String config)
    {
        return JSON.parseObject(config, UserVo.UserConfig.class);
    }

    default String userConfigToStrConfig(UserVo.UserConfig userConfig)
    {
        return JSON.toJSONString(userConfig);
    }
}

