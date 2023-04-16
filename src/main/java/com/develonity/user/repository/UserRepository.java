package com.develonity.user.repository;

import com.develonity.cache.CacheNames;
import com.develonity.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  @Cacheable(cacheNames = CacheNames.LOGIN_ID, key="'login'+#p0", unless="#result==null")
  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);

  List<User> findAllByIdIn(List<Long> userIds);
}
