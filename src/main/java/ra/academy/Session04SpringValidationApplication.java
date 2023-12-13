package ra.academy;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // ấu hình dự án spring boot
public class Session04SpringValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Session04SpringValidationApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
