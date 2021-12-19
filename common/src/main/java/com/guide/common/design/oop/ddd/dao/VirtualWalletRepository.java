package com.guide.common.design.oop.ddd.dao;

import com.guide.common.design.oop.ddd.data.VirtualWalletEntity;

import java.math.BigDecimal;

public interface VirtualWalletRepository
{
    VirtualWalletEntity getWalletEntity(Long walletId);

    BigDecimal getBalance(Long walletId);

    void updateBalance(Long walletId, BigDecimal balance);
}
