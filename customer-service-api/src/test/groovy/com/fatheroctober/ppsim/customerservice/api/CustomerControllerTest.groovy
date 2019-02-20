package com.fatheroctober.ppsim.customerservice.api

import com.fatheroctober.ppsim.customerservice.api.config.CustomerControllerTestConfig
import com.fatheroctober.ppsim.customerservice.model.Transaction
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [CustomerControllerTestConfig.class])
class CustomerControllerTest extends CustomerControllerTestBase {

    def "controller returns successful transaction response"() {
        when:
        def result = mockMvc.perform(post('/ppsim/api/v1/auth')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("simple.rq.json")))

        then:
        1 * customerService.auth(*_) >> { arguments ->
            assert arguments[0] == '123412341234'
            assert arguments[1] == '12/12'
            assert arguments[2] == '123'
            new Transaction(777)
        }

        expect:
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(resource("simple.rs.json")))
    }

    def "controller returns bad-request response"() {
        when:
        def result = mockMvc.perform(post('/ppsim/api/v1/auth')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("badFormat.rq.json")))

        then:
        result.andExpect(status().isBadRequest())
    }
}
