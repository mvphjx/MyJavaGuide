package com.guide.common.design.chain.impl.login.failed;

import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 展示Ws错误信息
 *
 * @author han
 */
@Slf4j
public class WsErrorInfoStrategy implements StrategyHandler<LoginInfo, String>
{
    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    @Override
    public String apply(LoginInfo param)
    {
        log.info("ErrorPageStrategy:展示Ws错误信息");
        return "<ws>不满足登录条件</ws>";
    }
}
