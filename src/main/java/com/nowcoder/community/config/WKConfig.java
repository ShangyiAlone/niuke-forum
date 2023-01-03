package com.nowcoder.community.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @BelongsProject: niuke-forum
 * @BelongsPackage: com.nowcoder.community.config
 * @Author: lilei
 * @CreateTime: 2023-01-02  13:21
 * @Description: TODO
 * @Version: 1.0
 */
@Configuration
public class WKConfig {
    private static final Logger logger= LoggerFactory.getLogger(WKConfig.class);

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @PostConstruct
    public void init(){
        //创建WK目录如果不存在
        File file=new File(wkImageStorage);
        if(!file.exists()){
            file.mkdir();
            logger.info("创建WK图片目录:"+wkImageStorage);
        }
    }
}
