//package com.nowcoder.util;
//
//import com.morgan.service.JavaMailSenderImpl;
//import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
//import org.apache.velocity.app.VelocityEngine;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Service;
//
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeUtility;
//import java.util.Map;
//import java.util.Properties;
//import javax.mail.internet.InternetAddress;
//
///**
// * Created by Administrator on 2017/10/2 0002.
// */
//@Service
//public class MailSender implements InitializingBean{
//    private JavaMailSenderImpl mailSender;
//    private static final Logger logger= LoggerFactory.getLogger("MailSender.class");
//
//
//
//
//    private VelocityEngine  velocityEngine;
//
//
//    public boolean sendWithHTMLTemplate(String to, String subject, String template,
//                                        Map<String,Object> model){
//            try {
//                String nick = MimeUtility.encodeText("牛客网中级课");
//                InternetAddress from = new InternetAddress(nick + "course@morgan.com");
//                //MimeMessage mimeMessage=mailSender.createMimeMessage();
//                //MimeMessageHelper
//
//
//            }catch (Exception e){
//
//
//            }
//
//
//    }
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        mailSender=new JavaMailSenderImpl();
//        mailSender.setUsername("rxqw1997@gmail.com");
//        mailSender.setPassword("52xkj1977");
//        mailSender.setHost("smtp.qq.com");
//        mailSender.setPosr(465);
//        mailSender.setProtocol("smtps");
//        mailSender.setDefaultEncoding("utf8");
//        Properties javaMailProperties=new Properties();
//        javaMailProperties.put("mail.smtp",true);
//        mailSender.setJavaMailProperties(javaMailProperties);
//
//
//    }
//}
