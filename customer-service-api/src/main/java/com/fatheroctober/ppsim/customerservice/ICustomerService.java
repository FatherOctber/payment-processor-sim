package com.fatheroctober.ppsim.customerservice;

import com.fatheroctober.ppsim.customerservice.exception.CustomerServiceException;

public interface ICustomerService {
    String auth(String cardNumber, String expiryDate, String cvc2) throws CustomerServiceException;
}
