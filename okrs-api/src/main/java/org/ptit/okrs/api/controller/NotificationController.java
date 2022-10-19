package org.ptit.okrs.api.controller;

import org.ptit.okrs.core.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody NotificationRequestDTO notificationRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(notificationRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> updateNotification(@PathVariable("id") int notificationId,
                                                   @RequestBody NotificationRequestDTO notificationRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.updateNotification(notificationId, notificationRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> deleteNotification(@PathVariable("id") int notificationId){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteNotification(notificationId));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> listNotifications(){
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.listNotification());
    }

}
