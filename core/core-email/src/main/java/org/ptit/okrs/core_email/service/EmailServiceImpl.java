package org.ptit.okrs.core_email.service;

import lombok.extern.slf4j.Slf4j;
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

  }

  @Async
  @Override
  public void send(String subject, String to, String template, Map<String, Object> properties) {

  }
}
