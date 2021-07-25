package com.guide.common.design.chain.impl.login.failed;

import com.alibaba.fastjson.JSONObject;
import com.guide.common.design.chain.AbstractStrategyRouter;
import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用游客账号进行登录
 *
 * @author han
 */
@Slf4j
public class WebGuestStrategy implements StrategyHandler<LoginInfo, JSONObject>
{
    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    @Override
    public JSONObject apply(LoginInfo param)
    {
        log.info("GuestStrategy:使用游客账号登录");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", "Guest");
        return jsonObject;
    }
}
