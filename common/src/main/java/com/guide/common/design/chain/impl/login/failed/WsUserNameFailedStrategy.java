package com.guide.common.design.chain.impl.login.failed;

import com.alibaba.fastjson.JSONObject;
import com.guide.common.design.chain.AbstractStrategyRouter;
import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * WS服务器登录鉴权
 *
 * @author han
 */
@Slf4j
public class WsUserNameFailedStrategy extends AbstractStrategyRouter<LoginInfo, String>
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
        return (param) -> new WsErrorInfoStrategy();
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
        return applyStrategy(param);
    }
}
