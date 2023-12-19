package com.creelayer.marketplace.crm.common.handler;

public interface QueryHandler<I, R> {
    R ask(I income);
}
