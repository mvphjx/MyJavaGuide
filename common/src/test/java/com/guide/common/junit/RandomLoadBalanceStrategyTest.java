package com.guide.common.junit;

import com.guide.common.junit.demo1.ClientRequest;
import com.guide.common.junit.demo1.RandomLoadBalanceStrategy;
import com.guide.common.junit.demo1.ServerNode;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

/**
 * 随机负载均衡策略测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtils.class)
public class RandomLoadBalanceStrategyTest
{
    /** 定义测试对象 */
    /**
     * 随机负载均衡策略
     */
    //@InjectMocks
    private RandomLoadBalanceStrategy randomLoadBalanceStrategy = new RandomLoadBalanceStrategy();

    /**
     * 测试: 选择服务节点-第一个节点
     */
    @Test
    public void testSelectNodeWithFirstNode()
    {
        // 模拟依赖方法
        PowerMockito.mockStatic(RandomUtils.class);
        PowerMockito.when(RandomUtils.nextInt(Mockito.eq(0), Mockito.anyInt())).thenReturn(9);

        // 调用测试方法
        ServerNode serverNode1 = new ServerNode(1L, 10);
        ServerNode serverNode2 = new ServerNode(2L, 20);
        ServerNode serverNode3 = new ServerNode(3L, 30);
        List<ServerNode> serverNodeList = Arrays.asList(serverNode1, serverNode2, serverNode3);
        ClientRequest clientRequest = new ClientRequest();
        ServerNode serviceNode = randomLoadBalanceStrategy.selectNode(serverNodeList, clientRequest);
        Assert.assertEquals("服务节点不一致", serverNode1, serviceNode);

        // 验证依赖方法
        PowerMockito.verifyStatic(RandomUtils.class);
        int totalWeight = serverNodeList.stream().mapToInt(ServerNode::getWeight).sum();
        RandomUtils.nextInt(0, totalWeight);
    }
}
