package top.lldwb.file.saving.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Import({MinIOConfig.class})
public class PublicApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublicApplication.class, args);
    }
}
