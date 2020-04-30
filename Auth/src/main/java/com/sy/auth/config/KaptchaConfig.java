package com.sy.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rx.Producer;

/**
 * kaptcha 配置类
 * @author wangxiao
 * @since
 */
@Configuration
public class KaptchaConfig {


    private



    @Bean
    public Producer kaptchaProducer() {
//        Properties properties = new Properties();
//        properties.setProperty("kaptcha.image.width", "100");
//        properties.setProperty("kaptcha.image.height", "40");
//        properties.setProperty("kaptcha.textproducer.font.size", "32");
//        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
//        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
//        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
//
 //          DefaultKaptcha kaptcha = new DefaultKaptcha();
//        Config config = new Config(properties);
//        kaptcha.setConfig(config);

        return kaptcha;
    }
}
