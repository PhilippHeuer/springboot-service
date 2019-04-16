package service;

import com.github.philippheuer.events4j.Events4JModule;
import com.github.philippheuer.swaggerwebflux.WebFluxSwaggerModule;
import com.github.philippheuer.webfluxerror.WebFluxErrorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebFluxSwaggerModule.class, Events4JModule.class, WebFluxErrorModule.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
