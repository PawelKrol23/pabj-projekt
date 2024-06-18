package org.example.serwisogloszen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SerwisOgloszenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerwisOgloszenApplication.class, args);
    }

}
