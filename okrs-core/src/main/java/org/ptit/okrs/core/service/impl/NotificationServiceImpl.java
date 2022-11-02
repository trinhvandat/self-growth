package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.entity.Notification;
import org.ptit.okrs.core.model.NotificationResponse;
import org.ptit.okrs.core.repository.NotificationRepository;
import org.ptit.okrs.core.service.NotificationService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
        log.info("(create)content : {}, userId : {}", content, userId);
        var notification = Notification.of(content, userId);
        notification = create(notification);
        String.format("<b>"+content+"</b>");
        return NotificationResponse.from(notification);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<NotificationResponse> getById(String id) {
        log.info("(getById)id: {}", id);
        return repository.findById(id).stream()
                .map(NotificationResponse::from)
                .collect(Collectors.toList());
}

    @Override
    public Page<NotificationResponse> list(String userId, final Pageable pageable) {
        log.info("(list)userId : {}", userId);
        return repository.findByUserId(userId, pageable).map(NotificationResponse::from);
    }

    @Override
    public List<Notification> searchByDateLessThan(long date, int page, int size) {
      if (log.isDebugEnabled()) log.debug("(searchByDateLessThan)date: {}, page: {}, size: {}", date, page, size);
      return new ArrayList<>();
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
