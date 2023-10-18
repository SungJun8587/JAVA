package com.dev.BbsMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${APP_ENV:local}.properties") // Properties 파일들을 선언
public class BbsMvcApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BbsMvcApplication.class, args);
	}
}
