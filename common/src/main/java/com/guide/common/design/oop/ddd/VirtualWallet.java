package com.guide.common.design.oop.ddd;

import java.math.BigDecimal;

/**
 * 虚拟钱包
 * Domain领域模型(充血模型)
 *
 * 职责，负责处理业务逻辑：
 * 收入支出
 * 冻结
 * 透支
 *
 */

public class VirtualWallet
{
    private Long id;
    private Long createTime = System.currentTimeMillis();
    ;
    private BigDecimal balance = BigDecimal.ZERO;
    private boolean isAllowedOverdraft = true;
    private BigDecimal overdraftAmount = BigDecimal.ZERO;
    private BigDecimal frozenAmount = BigDecimal.ZERO;

    public VirtualWallet(Long preAllocatedId)
    {
        this.id = preAllocatedId;
    }

    /**
     * 冻结
     *
     * @param amount
     */
    public void freeze(BigDecimal amount)
    {
    }

    public void unfreeze(BigDecimal amount)
    {
    }

    /**
     * 调整透支额度
     *
     * @param amount
     */
    public void increaseOverdraftAmount(BigDecimal amount)
    {
    }

    public void decreaseOverdraftAmount(BigDecimal amount)
    {
    }

    public void closeOverdraft()
    {
    }

    /**
     * 开启透支
     */
    public void openOverdraft()
    {
    }

    public BigDecimal balance()
    {
        return this.balance;
    }

    public BigDecimal getAvaliableBalance()
    {
        BigDecimal totalAvaliableBalance = this.balance.subtract(this.frozenAmount);
        if (isAllowedOverdraft)
        {
            totalAvaliableBalance.add(this.overdraftAmount);
        }
        return totalAvaliableBalance;
    }

    /**
     *支出
     * @param amount
     */
    public void debit(BigDecimal amount)
    {
        BigDecimal totalAvaliableBalance = getAvaliableBalance();
        if (totalAvaliableBalance.compareTo(amount) < 0)
        {
            throw new RuntimeException();
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * 收入
     * @param amount
     */
    public void credit(BigDecimal amount)
    {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
        {
            throw new RuntimeException();
        }
        this.balance = this.balance.add(amount);
    }
}
