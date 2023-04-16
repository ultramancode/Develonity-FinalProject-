package com.develonity.admin.dto;


import com.develonity.user.entity.User;
import com.develonity.user.entity.UserRole;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
// user전체 회원을 조회에 사용되는 dto (로그인id, 실명, 별명, 유저역할)
//redis 캐싱 위해 Serializable 구현
public class UserResponse implements Serializable {

  private final Long id;
  private final String loginId;

  private final String nickName;

  private final UserRole userRole;

  @Builder
  public UserResponse(User user) {
    this.id = user.getId();
    this.loginId = user.getLoginId();
    this.nickName = user.getNickname();
    this.userRole = user.getUserRole();
  }


}


