package com.fhs.trans.untrans.config;

import com.fhs.trans.untrans.driver.MysqlUnTransDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UnTransDriverConfig {

    @Bean
    @ConditionalOnProperty(name = "easy-trans.db-type", havingValue = "mysql")
    public MysqlUnTransDriver mysqlUnTransDriver() {
        MysqlUnTransDriver result = new MysqlUnTransDriver();
        return result;
    }

}
