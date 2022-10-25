package org.ptit.okrs.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ptit.okrs.core.entity.Notification;

@Data
@NoArgsConstructor
public class NotificationResponse {

    private String id;

    private String content;

    private String userId;

    public static NotificationResponse from(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setContent(notification.getContent());
        response.setUserId(notification.getUserId());
        return response;
    }
}
