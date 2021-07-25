package com.guide.common.design.chain.impl.login.failed;

import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用游客账号进行登录
 *
 * @author han
 */
@Slf4j
public class WebErrorPageStrategy implements StrategyHandler<LoginInfo, String>
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
        log.info("ErrorPageStrategy:展示Web错误页");
        return "<html>不满足登录条件</html>";
    }
}
