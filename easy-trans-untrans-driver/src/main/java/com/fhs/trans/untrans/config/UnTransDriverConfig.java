package com.fhs.trans.untrans.config;

import com.fhs.trans.untrans.driver.CommonUnTransDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UnTransDriverConfig {

    @Bean
    public CommonUnTransDriver mysqlUnTransDriver() {
        CommonUnTransDriver result = new CommonUnTransDriver();
        return result;
    }


}
