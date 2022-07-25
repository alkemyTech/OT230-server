package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.EmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${alkemy.ong.email.sender}")
    private String emailSender;
    @Value("${alkemy.ong.email.apikey}")
    private String sendGridApiKey;
    @Value("${alkemy.ong.email.templateid}")
    private String templateIDRegister;

    @Value("${alkemy.ong.email.templateidcontact}")
    private String templateIDContact;

    public void switchEmail(String to, Integer templateId) throws IOException{
        switch (templateId){
            case 1: preparateMail(to, "Gracias!", "Contenido", templateIDRegister);
                    break;
            case 2:preparateMail(to,"Somos mas Ong", "Muchas gracias por " +
                    "contactarte con nosotros, te mandaremos un mensaje a la brevedad", templateIDContact);
                    break;
        }
    }

    @Override
    public void preparateMail(String to, String subject, String contentValue, String template) throws IOException{

        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content("text/html", contentValue);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        mail.setTemplateId(template);
        sendMail(mail);
    }

    public void sendMail(Mail mail) throws IOException{
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(request.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}