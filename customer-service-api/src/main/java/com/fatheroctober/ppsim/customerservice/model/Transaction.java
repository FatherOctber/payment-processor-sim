package com.fatheroctober.ppsim.customerservice.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Transaction {
    @Getter
    private String id;
}
