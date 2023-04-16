package com.develonity.common.security.users;

import com.develonity.common.aop.LogExecution;
import com.develonity.common.auth.UserDetailsServiceType;
import com.develonity.user.entity.User;
import com.develonity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserAdminDetailsService {

  private final UserRepository userRepository;

  @Override
  @LogExecution
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    User user = userRepository.findByLoginId(loginId)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    return new UserDetails(user, loginId, user.getId());
  }
  public UserDetailsServiceType getServiceType(){
    return UserDetailsServiceType.USER;
  }
}
