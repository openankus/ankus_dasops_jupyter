package org.ankus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"org.ankus.repository"}) // com.my.jpa.repository 하위에 있는 jpaRepository를 상속한 repository scan
//@EntityScan(basePackages = {"org.ankus.model"}) // com.my.jpa.entity 하위에 있는 @Entity 클래스 scan
//@ComponentScan({"org.ankus.*"})
public class JupyterApplication {
    public static void main(String[] args) {
        SpringApplication.run(JupyterApplication.class, args);
    }
}
