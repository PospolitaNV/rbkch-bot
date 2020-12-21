package com.npospolita.rbkchbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = { "com.npospolita" },
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.npospolita.rbkchbot.deprecated.*"))
public class RbkchBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbkchBotApplication.class, args);
    }

}
