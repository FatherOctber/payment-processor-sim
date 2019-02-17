package com.fatheroctober.ppsim.customerservice.api

import com.fatheroctober.ppsim.customerservice.ICustomerService
import com.fatheroctober.ppsim.customerservice.api.config.CustomerServiceTestConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.ResourceUtils
import spock.lang.Specification

@ContextConfiguration(classes = [CustomerServiceTestConfig.class])
class CustomerControllerTestBase extends Specification {
    @Autowired
    ICustomerService customerService

    @Autowired
    CustomerController customerController

    @Autowired
    CustomerControllerAdvice customerControllerAdvice

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(customerControllerAdvice)
                .setRemoveSemicolonContent(false)
                .build()
    }

    def resource = { resourcePath
        -> ResourceUtils.getFile(this.getClass().getResource(resourcePath)).text
    }
}
