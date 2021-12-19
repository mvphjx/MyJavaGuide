package com.guide.common.design.oop.ddd.dao;

import com.guide.common.design.oop.ddd.data.VirtualWalletTransactionEntity;

public interface VirtualWalletTransactionRepository
{
    void saveTransaction(VirtualWalletTransactionEntity transactionEntity);
}
