package com.fatheroctober.ppsim.customerservice;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Override
    public String auth(String cardNumber, String expiryDate, String cvc2) {
        return "Token";
    }
}
