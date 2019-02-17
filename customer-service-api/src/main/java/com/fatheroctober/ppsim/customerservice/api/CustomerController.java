package com.fatheroctober.ppsim.customerservice.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/ppsim/api/v1", produces = "application/json")
public class CustomerController {

    @PostMapping("/auth")
    public TransactionRs auth(@Valid @RequestBody CardInfo cardInfo) {
        return new TransactionRs().status(Status.SUCCESS);
    }
}
