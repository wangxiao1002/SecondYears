package com.sy.auth.mail;


import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.sy.auth.utils.MailUtil;
import com.sy.basis.domain.MailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * bus 消费者 发送邮件
 * @author wangxiao
 * @since 1.1
 */
@Service
public class MailBusListener {



    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    @Qualifier("asyncEventBus")
    private AsyncEventBus asyncEventBus;


    private MailUtil mailUtil;


    @PostConstruct
    public void init(){
        mailUtil = MailUtil.getInstance(javaMailSender,templateEngine);
        asyncEventBus.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void  sendMail (MailEntity mail) {
        mailUtil.sendMail(mail);
    }



}
