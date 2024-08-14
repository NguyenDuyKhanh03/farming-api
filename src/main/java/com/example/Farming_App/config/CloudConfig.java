package com.example.Farming_App.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

    @Bean
    public Cloudinary cloudinaryConfig(){
        Cloudinary cloudinary=null;
        Map config= new HashMap();
        config.put("cloud_name","dkzvrtfvc");
        config.put("api_key", "757517974881382");
        config.put("api_secret", "KpykWNg_2UDI9bo0M9LeqYvzKSk");
        config.put("secure",true);
        cloudinary=new Cloudinary(config);
        return cloudinary;
    }
}
