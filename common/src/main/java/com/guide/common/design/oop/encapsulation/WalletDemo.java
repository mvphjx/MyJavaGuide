package com.guide.common.design.oop.encapsulation;

import cn.hutool.core.util.IdUtil;

import java.math.BigDecimal;

/**
 * 钱包类的封装
 * 封装也叫作信息隐藏或者数据访问保护。
 * 这里通过使用private配合get、set方法，对属性进行了访问控制。
 * 优点：屏蔽了复杂度，提高可读性、可维护性、易用性
 */
public class WalletDemo
{
    private String id;
    private long createTime;
    private BigDecimal balance;
    private long balanceLastModifiedTime;
    // ...省略其他属性...

    public WalletDemo()
    {
        this.id = IdUtil.fastUUID();
        this.createTime = System.currentTimeMillis();
        this.balance = BigDecimal.ZERO;
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    // 注意：下面对get方法做了代码折叠，是为了减少代码所占文章的篇幅
    public String getId()
    {
        return this.id;
    }

    public long getCreateTime()
    {
        return this.createTime;
    }

    public BigDecimal getBalance()
    {
        return this.balance;
    }

    public long getBalanceLastModifiedTime()
    {
        return this.balanceLastModifiedTime;
    }

    public void increaseBalance(BigDecimal increasedAmount)
    {
        if (increasedAmount.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new RuntimeException("...");
        }
        this.balance.add(increasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }

    public void decreaseBalance(BigDecimal decreasedAmount)
    {
        if (decreasedAmount.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new RuntimeException("...");
        }
        if (decreasedAmount.compareTo(this.balance) > 0)
        {
            throw new RuntimeException("...");
        }
        this.balance.subtract(decreasedAmount);
        this.balanceLastModifiedTime = System.currentTimeMillis();
    }
}
