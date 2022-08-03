package com.example.webproject01;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilde5r configure(SpringApplicationBuilder application) {
        return application.sources(Webproject01Application.class);
    }

}
