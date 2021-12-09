package com.guide.common.json;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.guide.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
public class JsonTest
{
    public static void main(String[] args) throws JsonProcessingException
    {
        UserBo userBo = new UserBo();
        userBo.setId(1L);
        userBo.setName("Name");
        userBo.setSex(1);
        userBo.setPassword("PassWord");
        userBo.setCreateTime(Timestamp.valueOf(DateTime.now().toLocalDateTime()));
        log.info("Init BO");
        log.info(JsonUtil.createJsDataByJackson(userBo));
        log.info("Convert BO->VO");
        UserVo userVo = JsonUtil.convert(UserVo.class, userBo);
        userVo.fromObject();
        log.info(JsonUtil.createJsDataByJackson(userVo));
        System.out.println();
        log.info("Convert VO->BO");
        userBo = null;
        userBo = JsonUtil.convert(UserBo.class, userVo);
        log.info(JsonUtil.createJsDataByJackson(userBo));
    }
}
