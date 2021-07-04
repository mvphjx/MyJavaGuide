package com.guide.common.concurrent.biz;

public interface MyFuture <V> {
    V get() throws Exception ;
}
