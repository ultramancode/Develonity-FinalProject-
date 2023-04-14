package com.develonity.user.service;

import com.amazonaws.thirdparty.apache.http.impl.bootstrap.HttpServer;
import com.develonity.user.dto.LoginRequest;
import com.develonity.user.dto.ProfileRequest;
import com.develonity.user.dto.ProfileResponse;
import com.develonity.user.dto.RegisterRequest;
import com.develonity.user.dto.TokenResponse;
import com.develonity.user.entity.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  User register(RegisterRequest registerRequest);

  TokenResponse login(LoginRequest loginRequest);

  void withdrawal(User user, String password);

  void logout(String loginId, HttpServletRequest request);

  ProfileResponse getProfile(Long userId);

  TokenResponse reissue(String refreshToken);

  void subtractGiftPoint(int giftPoint, User user);

  void addGiftPoint(int giftPoint, User user);

  void addRespectPoint(int respectPoint, User user);

  void upgradeGrade(Long userId);

  boolean isLackedRespectPoint(Long userId);

  User getUserAndCheck(Long userId);

  //프로필 수정
  void updateProfile(ProfileRequest request,
      MultipartFile multipartFile, User user) throws IOException;

  //preSignedUrl 방식
  void updateProfileByPreSignedUrl(ProfileRequest request, String imagePath, User user);

  //이미지 업로드
  void uploadOne(MultipartFile multipartFile, Long userId) throws IOException;

  //이미지 삭제
  void deleteProfileImage(Long userId);

  boolean existsByUserId(Long userId);

  HashMap<Long, String> getUserIdAndNickname(List<Long> userIds);
}