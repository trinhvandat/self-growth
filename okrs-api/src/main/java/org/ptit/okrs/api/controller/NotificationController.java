package org.ptit.okrs.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.api.model.request.NotificationRequest;
import org.ptit.okrs.api.model.response.NotificationResponse;
import org.ptit.okrs.core.entity.Notification;
import org.ptit.okrs.core.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.ptit.okrs.api.constant.OkrsApiConstant.BaseUrl.NOTIFICATION_BASE_URL;

@RestController
@RequestMapping(NOTIFICATION_BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@Validated @RequestBody NotificationRequest notificationRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNotification(notificationRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(@Validated @PathVariable("id") int notificationId,
                                                                   @RequestBody NotificationRequest notificationRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateNotification(notificationId, notificationRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationResponse> deleteNotification(@PathVariable("id") int notificationId) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteNotification(notificationId));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> listNotifications() {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.listNotification());
    }

}
