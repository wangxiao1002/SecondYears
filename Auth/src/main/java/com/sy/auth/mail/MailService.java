package com.sy.auth.mail;

import com.google.common.eventbus.AsyncEventBus;
import com.sy.basis.domain.MailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;




/**
 * 邮件服务
 * @author wangxiao
 * @since 1.1
 */
@Service
public class MailService {




    @Autowired
    @Qualifier("asyncEventBus")
    private AsyncEventBus asyncEventBus;





    public void sendMail (MailEntity mail) {
        asyncEventBus.post(mail);
    }


}
