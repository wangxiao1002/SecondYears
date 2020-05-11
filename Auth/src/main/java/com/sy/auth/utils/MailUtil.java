package com.sy.auth.utils;

import com.sy.basis.domain.MailEntity;
import com.sy.basis.util.StringUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

/**
 * 邮件发送工具
 * @author wangxiao
 * @since 1.1
 */
public class MailUtil {

    private  static JavaMailSender JAVA_MAIL_SENDER;

    private static TemplateEngine TEMPLATE_ENGINE;

    public MailUtil(JavaMailSender javaMailSender,TemplateEngine templateEngine) {
        JAVA_MAIL_SENDER = javaMailSender;
        TEMPLATE_ENGINE = templateEngine;
    }

    public static void sendMail (MailEntity mail) {
        Assert.notNull(mail,"mail must not null  !!");

        switch (mail.getType()) {
            case NORMAL:
                sendNormalMail(mail);
                break;
            case HTML:
                sendHtmlMail(mail);
                break;
            default:
                break;
        }
    }


    private static void sendNormalMail (MailEntity email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(email.getFrom());
        mailMessage.setTo(email.getTo());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getContent());
        JAVA_MAIL_SENDER.send(mailMessage);
    }

    private static  void sendHtmlMail (MailEntity email) {
        String content = email.getContent();
        if (email.getVariables() != null) {
            content = generate(email);
        }
        MimeMessage mailMessage = JAVA_MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true);
            messageHelper.setFrom(email.getFrom());
            messageHelper.setTo(email.getTo());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(content,true);
            if (!StringUtil.isEmpty(email.getAttachPath())) {
                FileSystemResource file = new FileSystemResource(new File(email.getAttachPath()));
                messageHelper.addAttachment(Objects.requireNonNull(file.getFilename()),file);
            }
            JAVA_MAIL_SENDER.send(mailMessage);
        }catch (MessagingException e) {

        }


    }

    private static String generate (MailEntity mail) {

        Context context = new Context();
        context.setVariables(mail.getVariables());
        return TEMPLATE_ENGINE.process(mail.getTemplateName(), context);

    }
}
