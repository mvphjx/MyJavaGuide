package com.guide.common.design.ddd.demo.service;

import com.guide.common.design.ddd.demo.bzo.OnSaleContext;
import com.guide.common.design.ddd.demo.bzo.OnSaleNormalItemCmd;
import com.guide.common.design.ddd.demo.bzo.Response;
import com.guide.common.design.ddd.demo.phase.check.OnSaleDataCheckPhase;
import com.guide.common.design.ddd.demo.phase.init.OnSaleContextInitPhase;
import com.guide.common.design.ddd.demo.phase.process.OnSaleProcessPhase;

/**
 *商品上架流程，重构：
 *  1）分为三个阶段每个阶段有若干过程
 *  2）领域建模，实现内聚性、复用性
 */
public class OnSaleNormalItemCmdExe
{
    private OnSaleContextInitPhase onSaleContextInitPhase;
    private OnSaleDataCheckPhase onSaleDataCheckPhase;
    private OnSaleProcessPhase onSaleProcessPhase;

    /**
     * 执行上架操作
     * @param cmd
     * @return
     */
    public Response execute(OnSaleNormalItemCmd cmd) {

        OnSaleContext onSaleContext = init(cmd);

        checkData(onSaleContext);

        process(onSaleContext);

        return Response.buildSuccess();
    }

    private OnSaleContext init(OnSaleNormalItemCmd cmd) {
        return onSaleContextInitPhase.init(cmd);
    }

    private void checkData(OnSaleContext onSaleContext) {
        onSaleDataCheckPhase.check(onSaleContext);
    }

    private void process(OnSaleContext onSaleContext) {
        onSaleProcessPhase.process(onSaleContext);
    }
}
