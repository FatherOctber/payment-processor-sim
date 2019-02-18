package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.customerservice.exception.CustomerServiceException;
import com.fatheroctober.ppsim.customerservice.model.Transaction;

public interface ICustomerService {
    Transaction auth(String cardNumber, String expiryDate, String cvc2) throws CustomerServiceException;
}
