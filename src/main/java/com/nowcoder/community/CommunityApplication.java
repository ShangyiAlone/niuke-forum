package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class CommunityApplication {

    @PostConstruct
    public void init() {
        // 解决netty启动冲突问题
        // see Netty4Utils.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        // 配置发帖上传图片的大小限制
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小,10M
        factory.setMaxFileSize("10240KB"); // KB,MB
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
