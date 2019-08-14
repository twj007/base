package com.mall.pojo;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/14
 **/
public class SerizableMimeMessage extends MimeMessage implements Serializable {
    public SerizableMimeMessage(Session session) {
        super(session);
    }
}
