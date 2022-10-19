package org.ptit.okrs.core.service;

import org.ptit.okrs.core.entity.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
  T create(T t);
  void delete(String id);
  T find(String id);
  boolean isExist(String id);
  T update(T t);
  List<T> list();
}
