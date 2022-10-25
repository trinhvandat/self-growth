package org.ptit.okrs.core.service;

import org.ptit.okrs.core.constant.OkrsTimeType;
import org.ptit.okrs.core.constant.OkrsType;
import org.ptit.okrs.core.entity.Notification;
import org.ptit.okrs.core.model.NotificationResponse;
import org.ptit.okrs.core.model.ObjectiveDetailResponse;
import org.ptit.okrs.core.model.ObjectiveResponse;
import org.ptit.okrs.core.service.base.BaseService;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    /**
     * Create new notification
     * @param content - content of the notification
     * @param userId - id of the current user who logged in our system
     * @return notification
     */
    NotificationResponse create(String content, String userId);

    /**
     * Delete notification by its id
     * @param id - id of notification
     */
    void deleteById(String id);

    /**
     * get a notification by its id
     * @param id - id of notification
     * @return notification by id
     */
    NotificationResponse getById(String id);

    /**
     * list all notification
     * @param userId - id of user is logging
     * @return a list of notification
     */
    List<NotificationResponse> list(String userId);

    /**
     * update an objective
     * @param content - content of this notification
     * @param userId - id of the user that own this notification
     * @return the updated notification
     */
    NotificationResponse update(
            String id,
            String content,
            String userId);
}



