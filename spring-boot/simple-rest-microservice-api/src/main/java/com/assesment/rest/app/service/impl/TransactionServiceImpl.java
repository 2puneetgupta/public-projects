package com.assesment.rest.app.service.impl;

import com.assesment.rest.app.model.Transactions;
import com.assesment.rest.app.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public boolean insert(Transactions transactions) {
        System.out.println("Inserting : ");
        System.out.println(transactions);
        return true;
    }
}
