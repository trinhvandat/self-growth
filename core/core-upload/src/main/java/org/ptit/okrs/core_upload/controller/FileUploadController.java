package org.ptit.okrs.core_upload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_upload.service.CoreUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileUploadController {
  private final CoreUploadService service;

  @PostMapping(consumes="multipart/form-data", path = "/uploads")
  @ResponseStatus(HttpStatus.OK)
  public String uploadFile(
      @RequestParam(name = "file", required = false) MultipartFile file) {
    log.info("(uploadFile)fileName: {}", file.getOriginalFilename());
    return service.uploadFile(file);
  }
}
