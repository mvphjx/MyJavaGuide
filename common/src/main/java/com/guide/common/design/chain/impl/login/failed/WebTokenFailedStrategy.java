package com.guide.common.design.chain.impl.login.failed;

import com.alibaba.fastjson.JSONObject;
import com.guide.common.design.chain.AbstractStrategyRouter;
import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Web服务器登录鉴权
 *
 * @author han
 */
@Slf4j
public class WebTokenFailedStrategy extends AbstractStrategyRouter<LoginInfo, JSONObject>
        implements StrategyHandler<LoginInfo, String>
{
    /**
     * 抽象方法，需要子类实现策略的分发逻辑
     *
     * @return 分发逻辑 Mapper 对象
     */
    @Override
    protected StrategyMapper registerStrategyMapper()
    {
        return (param) -> new WebGuestStrategy();
    }

    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    @Override
    public String apply(LoginInfo param)
    {
        log.info("WebTokenFailedStrategy:登录失败处理策略");
        JSONObject jsonObject = this.applyStrategy(param);
        return jsonObject.toJSONString();
    }
}
