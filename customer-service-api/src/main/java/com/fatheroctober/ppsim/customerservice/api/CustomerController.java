package com.fatheroctober.ppsim.customerservice.api;

import com.fatheroctober.ppsim.customerservice.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ppsim/api/v1", produces = "application/json")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private Formatter formatter;

    @PostMapping("/auth")
    public TransactionRs auth(@Valid @RequestBody final CardInfo cardInfo) {
        formatter.validateCardInfo(cardInfo);
        String transId = customerService.auth(cardInfo.getCardNumber(), cardInfo.getExpirationDate(), cardInfo.getCvc2());
        return new TransactionRs()
                .transactionId(transId)
                .status(Status.SUCCESS);
    }
}
