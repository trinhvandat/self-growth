package org.ptit.okrs.core_email.service;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_exception.InternalServerError;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;

import java.util.Map;

@Slf4j
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender emailSender;
  private final TemplateEngine templateEngine;
  private final String emailFrom;

  public EmailServiceImpl(JavaMailSender emailSender, TemplateEngine templateEngine, String emailFrom) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.emailFrom = emailFrom;
  }

  @Async
  @Override
  public void send(String subject, String to, String content) {
    log.info("(send)subject: {}, to: {}, content: {}", subject, to, content);
    try {
      var message = new SimpleMailMessage();
      message.setFrom(emailFrom);
      message.setTo(to);
      message.setSubject(subject);
      message.setText(content);
      emailSender.send(message);
    } catch (Exception ex) {
      log.error("(send)to: {}, subject: {}, ex: {}", to, subject, ex.getMessage());
      throw new InternalServerError("Send mail failed to email: {}" + to);
    }
  }

  @Async
  @Override
  public void send(String subject, String to, String template, Map<String, Object> properties) {

  }
}
