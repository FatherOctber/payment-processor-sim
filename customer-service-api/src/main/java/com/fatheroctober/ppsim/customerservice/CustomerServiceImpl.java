package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.customerservice.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Override
    public Transaction auth(String cardNumber, String expiryDate, String cvc2) {
        return Transaction.builder().id("Token").build();
    }
}
