package com.develonity.common.auth;

import com.develonity.common.redis.RedisDao;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class UserAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final RedisDao redisDao;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String accessToken = jwtUtil.resolveAccessToken(request);
      if (accessToken != null) {
        Object blackList = redisDao.getBlackList(accessToken);
        if (blackList != null) {
          if (blackList.equals("logout")) {
            throw new IllegalArgumentException("로그아웃된 토큰입니다.");
          }
        }
          String loginId = jwtUtil.getLoginIdFromTokenIfValid(accessToken);
          Authentication authentication = jwtUtil.createAuthentication(loginId,
              UserDetailsServiceType.USER);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }
}

