package com.develonity.common.auth;

import com.develonity.common.security.users.UserAdminDetailsService;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsFactory {
  private final List<UserAdminDetailsService> userAdminDetailsServiceList;
  private HashMap<UserDetailsServiceType, UserAdminDetailsService> serviceMap = new HashMap<>();

  public UserDetailsFactory(List<UserAdminDetailsService> userAdminDetailsServiceList) {
    this.userAdminDetailsServiceList = userAdminDetailsServiceList;
    for(UserAdminDetailsService userAdminDetailsService : userAdminDetailsServiceList){
      serviceMap.put(userAdminDetailsService.getServiceType(), userAdminDetailsService);
    }
  }

  public UserDetails getUserDetails(String loginId,UserDetailsServiceType userDetailsServiceType){
    UserAdminDetailsService userDetailsService = serviceMap.get(userDetailsServiceType);
        return userDetailsService.loadUserByUsername(loginId);
  }

}
