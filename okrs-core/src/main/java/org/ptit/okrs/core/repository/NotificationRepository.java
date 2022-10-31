package org.ptit.okrs.core.repository;

import org.ptit.okrs.core.entity.Notification;
import org.ptit.okrs.core.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {

    List<Notification> findByUserId(String userId);
}
