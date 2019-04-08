package net.bigmir;

import net.bigmir.model.UserRole;
import net.bigmir.services.UserService;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling
public class ZipSecurityApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZipSecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                userService.addUser("Sasha","5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.ADMIN);
                userService.addUser("User","5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER);

            }
        };
    }
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
