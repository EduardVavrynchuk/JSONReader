package com.json.configs;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
