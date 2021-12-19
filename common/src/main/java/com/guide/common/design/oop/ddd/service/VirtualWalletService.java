package com.guide.common.design.oop.ddd.service;

import com.guide.common.design.oop.ddd.TransactionType;
import com.guide.common.design.oop.ddd.VirtualWallet;
import com.guide.common.design.oop.ddd.data.VirtualWalletEntity;
import com.guide.common.design.oop.ddd.dao.VirtualWalletRepository;
import com.guide.common.design.oop.ddd.data.VirtualWalletTransactionEntity;
import com.guide.common.design.oop.ddd.dao.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

/**
 * 虚拟钱包服务
 *
 * 职责，负责处理通用逻辑：
 * 对象类型转换
 * 数据持久化
 *
 *
 */
public class VirtualWalletService
{
    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWallet getVirtualWallet(Long walletId)
    {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet = convert(walletEntity);
        return wallet;
    }



    public BigDecimal getBalance(Long walletId)
    {
        return walletRepo.getBalance(walletId);
    }

    //@Transactional
    public void debit(Long walletId, BigDecimal amount)
    {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet  = convert(walletEntity);
        wallet.debit(amount);
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(TransactionType.DEBIT);
        transactionEntity.setFromWalletId(walletId);
        transactionRepo.saveTransaction(transactionEntity);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    //@Transactional
    public void credit(Long walletId, BigDecimal amount)
    {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWallet wallet = convert(walletEntity);
        wallet.credit(amount);
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setType(TransactionType.CREDIT);
        transactionEntity.setFromWalletId(walletId);
        transactionRepo.saveTransaction(transactionEntity);
        walletRepo.updateBalance(walletId, wallet.balance());
    }

    //@Transactional
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount)
    {
        //...跟基于贫血模型的传统开发模式的代码一样...
    }

    /**
     * do 转换
     * @param walletEntity
     * @return
     */
    private VirtualWallet convert(VirtualWalletEntity walletEntity)
    {
        return null;
    }
}
