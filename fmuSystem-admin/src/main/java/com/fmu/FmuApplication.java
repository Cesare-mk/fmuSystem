package com.fmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author mm
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class FmuApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FmuApplication.class, args);
        System.out.println(" 启动成功  ");
    }
}
