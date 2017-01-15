package com.bind.ptw.be.initializers;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bind.ptw.be")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

  }


}
