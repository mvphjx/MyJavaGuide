package com.guide.common.jdbc;

import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Session;

public class SSHJdbcTest
{
    public static void main(String[] args) throws InterruptedException
    {
        //新建会话，此会话用于ssh连接
        Session session = JschUtil.getSession("192.168.130.150", 22, "hanjianxiang", "HelloHS10");
        System.out.println(session);
        boolean isBind = JschUtil.bindPort(session, "192.168.130.150", 3306, 3307);
        System.out.println(isBind);
        Thread.sleep(Long.MAX_VALUE);
        JschUtil.close(session);
    }
}
