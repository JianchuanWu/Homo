package org.homo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wujianchuan 2018/12/25
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.homo.*","org.hunter.pocket.*"})
@EnableCaching(proxyTargetClass = true)
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
