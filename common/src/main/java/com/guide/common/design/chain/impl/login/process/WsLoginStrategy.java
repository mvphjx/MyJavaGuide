package com.guide.common.design.chain.impl.login.process;

import com.guide.common.design.chain.AbstractStrategyRouter;
import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.failed.WsTokenFailedStrategy;
import com.guide.common.design.chain.impl.login.failed.WsUserNameFailedStrategy;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * WS服务器登录鉴权
 *
 * @author han
 */
@Slf4j
public class WsLoginStrategy extends AbstractStrategyRouter<LoginInfo, String>
        implements StrategyHandler<LoginInfo, String>
{
    private Map<String, StrategyHandler> handlerMap = new HashMap();

    {
        handlerMap.put("userName", new WsUserNameFailedStrategy());
        handlerMap.put("token", new WsTokenFailedStrategy());
    }

    /**
     * 抽象方法，需要子类实现策略的分发逻辑
     *
     * @return 分发逻辑 Mapper 对象
     */
    @Override
    protected StrategyMapper<LoginInfo, String> registerStrategyMapper()
    {
        return (param) -> handlerMap.get(param.getUserName() == null ? "token" : "userName");
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
        log.info("WebLoginStrategy:开始进行Ws服务器鉴权");
        return this.applyStrategy(param);
    }
}
