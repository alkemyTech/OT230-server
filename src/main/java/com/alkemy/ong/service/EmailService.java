package com.alkemy.ong.service;

import com.sendgrid.Mail;

import java.io.IOException;

public interface EmailService {

    public void switchEmail(String to, Integer templateId) throws IOException;

    public void preparateMail(String to, String subject, String contentValue, String template) throws IOException;

    public void sendMail(Mail mail) throws IOException;
}
