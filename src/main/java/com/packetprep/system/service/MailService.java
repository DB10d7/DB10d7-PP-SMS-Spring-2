package com.packetprep.system.service;


import com.packetprep.system.Model.NotificationEmail;
import com.packetprep.system.dto.MailRequest;
import com.packetprep.system.dto.MailResponse;
import com.packetprep.system.exception.SpringPPSystemException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    @Autowired
    private Configuration config;

        public MailResponse sendEmailForActivation(MailRequest request){
            Map<String, Object> map = new HashMap<>();
            map.put("name",request.getName());
            map.put("token",request.getToken());
            MailResponse response= new MailResponse();
            MimeMessage message= mailSender.createMimeMessage();
            try{
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


                Template template = config.getTemplate("email-template.html");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

                helper.setTo(request.getTo());
                helper.setText(html, true);
                helper.setSubject(request.getSubject());
                helper.setFrom("info@Packetprep.com");
                mailSender.send(message);

                response.setMessage("Mail Send To : " + request.getTo());
                response.setStatus(Boolean.TRUE);

            }catch(MessagingException | IOException | TemplateException e){
                response.setMessage("Mail Sending Failure : " + e.getMessage());
                response.setStatus(Boolean.FALSE);
            }

            return response;
        }

    public MailResponse sendEmailForForgetPassword(MailRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("name",request.getName());
        map.put("token",request.getToken());
        MailResponse response= new MailResponse();
        MimeMessage message= mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());


            Template template = config.getTemplate("forgetPasswordEmail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom("info@Packetprep.com");
            mailSender.send(message);

            response.setMessage("Mail Send To : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        }catch(MessagingException | IOException | TemplateException e){
            response.setMessage("Mail Sending Failure : " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }
}
