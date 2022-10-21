package org.ptit.okrs.test;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.ptit.okrs.core_email.configuration.EnableCoreEmail;
import org.ptit.okrs.core_email.constant.EmailConstant;
import org.ptit.okrs.core_email.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SpringBootApplication
@EnableCoreEmail
@RequestMapping("/api/v1/email")
public class MainApplicationTest {
  private final EmailService emailService;

  public static void main(String[] args){
    SpringApplication.run(MainApplicationTest.class, args);
  }

  @GetMapping("/text")
  @ResponseStatus(HttpStatus.OK)
  public String sendMailText(String to) {
     emailService.send("", to, "");
     return "send success";
  }

  @GetMapping("/html")
  @ResponseStatus(HttpStatus.OK)
  public String sendMailHtml(String to) {
    var properties = new HashMap<String, Object>();
    properties.put(EmailConstant.PARAM_TEMPLATE_NAME, "Toan");
    properties.put(EmailConstant.PARAM_TEMPLATE_CONTENT,"hello");
    emailService.send("Mail about test", to,"mail-template", properties);
    return "send success";
  }
}

