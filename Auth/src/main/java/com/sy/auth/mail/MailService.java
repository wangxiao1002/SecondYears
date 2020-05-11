package com.sy.auth.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.annotation.Resource;

/**
 * 邮件服务
 * @author wangxiao
 * @since 1.1
 */
@Service
public class MailService {


    @Resource
    private  JavaMailSender javaMailSender;

    @Autowired
    private  TemplateEngine templateEngine;



}
