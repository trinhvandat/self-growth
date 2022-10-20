package org.ptit.okrs.core_email.configuration;

import java.util.Properties;
import org.ptit.okrs.core_email.service.EmailService;
import org.ptit.okrs.core_email.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class CoreEmailConfiguration {

  @Value("${application.email.from:nguyensongtoan0302@gmail.com}")
  private String emailFrom;

  @Bean
  public EmailService emailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
    return new EmailServiceImpl(emailSender, templateEngine, emailFrom);
  }

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("your address mail");
    mailSender.setPassword("your password");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }
}
