package com.guide.common.design.chain.impl.login.before;

import com.guide.common.design.chain.AbstractStrategyRouter;
import com.guide.common.design.chain.StrategyHandler;
import com.guide.common.design.chain.impl.login.model.LoginInfo;
import com.guide.common.design.chain.impl.login.process.WsLoginStrategy;
import com.guide.common.design.chain.impl.login.process.WebLoginStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 责任树模式，解决复杂的if else条件判断
 * 假设业务场景如下：
 * 1.登录前策略：调用不同的校验服务器web、ws
 * 2.登录鉴权策略：使用token、用户名密码登录
 * 3.登录失败策略：使用游客登录，返回错误页
 * <p>
 * 优点：处理复杂的if else条件判断
 * 缺点：
 * 随着层级增加，策略数快速增长。
 * 相似策略需要进行抽象组合。
 *
 * @author han
 */
@Slf4j
public class StartLogin extends AbstractStrategyRouter<LoginInfo, String>
{
    private Map<String, StrategyHandler> handlerMap = new HashMap();

    {
        handlerMap.put("Web", new WebLoginStrategy());
        handlerMap.put("Ws", new WsLoginStrategy());
    }

    /**
     * 抽象方法，需要子类实现策略的分发逻辑
     *
     * @return 分发逻辑 Mapper 对象
     */
    @Override
    protected StrategyMapper registerStrategyMapper()
    {
        return new StrategyMapper<LoginInfo, String>()
        {
            /**
             * 根据入参获取到对应的策略处理者。可通过 if-else 实现，也可通过 Map 实现。
             *
             * @param param 入参
             * @return 策略处理者
             */
            @Override
            public StrategyHandler get(LoginInfo param)
            {
                return handlerMap.get(param.getType());
            }

        };
    }

    public static void main(String[] args)
    {
        LoginInfo loginInfo1 = new LoginInfo();
        loginInfo1.setType("Web");
        loginInfo1.setToken("ABCDEF");
        StartLogin startLogin = new StartLogin();
        log.info("开始处理流程", loginInfo1);
        String result = startLogin.applyStrategy(loginInfo1);
        System.out.println(result);
        LoginInfo loginInfo2 = new LoginInfo();
        loginInfo2.setType("Ws");
        loginInfo2.setUserName("hjx");
        log.info("开始处理流程", loginInfo2);
        result = startLogin.applyStrategy(loginInfo2);
        System.out.println(result);
    }
}
