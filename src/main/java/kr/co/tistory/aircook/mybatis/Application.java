package kr.co.tistory.aircook.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "kr.co.tistory.aircook.mybatis")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
