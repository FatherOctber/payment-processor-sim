package com.fatheroctober.ppsim.customerservice.api;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Formatter {
    @Value("${pan_regexp:[0-9]{12,16}}")
    String panRegexp;
    @Value("${expiry_date_regexp:^\\d{2}\\/\\d{2}$}")
    String expiryDateRegexp;
    @Value("${cvc2_regexp:[0-9]{3}}")
    String cvc2Regexp;

    public void validateCardInfo(CardInfo cardInfo) {
        validatePan(cardInfo.getCardNumber());
        validateExDate(cardInfo.getExpirationDate());
        validateCvc2(cardInfo.getCvc2());
    }

    private void validatePan(String pan) {
        Validate.notNull(pan, "Pan must be specified");
        if (!pan.matches(panRegexp)) {
            throw new IllegalArgumentException("Bad pan format");
        }
    }

    private void validateExDate(String exDate) {
        Validate.notNull(exDate, "Expiry date must be specified");
        if (!exDate.matches(expiryDateRegexp)) {
            throw new IllegalArgumentException("Bad expiry date format");
        }
    }

    private void validateCvc2(String cvc2) {
        Validate.notNull(cvc2, "Cvc2 must be specified");
        if (!cvc2.matches(cvc2Regexp)) {
            throw new IllegalArgumentException("Bad cvc2 format");
        }
    }
}
