package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.Notification;
import org.ptit.okrs.core.model.NotificationResponse;
import org.ptit.okrs.core.model.ObjectiveResponse;
import org.ptit.okrs.core.repository.NotificationRepository;
import org.ptit.okrs.core.service.NotificationService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NotificationServiceImpl extends BaseServiceImpl<Notification> implements NotificationService {

    private final NotificationRepository repository;

    public NotificationServiceImpl(NotificationRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public NotificationResponse create(String content, String userId) {
        return null;
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public List<NotificationResponse> getById(String id) {
        log.info("(getById)id: {}", id);
        return repository.findById(id).stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());
}

    @Override
    public List<NotificationResponse> list(String userId) {
        log.info("(list)userId : {}", userId);
        return repository.findByUserId(userId).stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse update(String id,String content, String userId) {
        var notification = find(id);
        if(notification == null){
            throw new NotFoundException(id, Notification.class.getSimpleName());
        }
        notification.setContent(content);
        notification.setUserId(userId);
        return NotificationResponse.from(update(notification));
    }
}
