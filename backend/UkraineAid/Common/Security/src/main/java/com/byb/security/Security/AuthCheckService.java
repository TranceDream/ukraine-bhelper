package com.byb.security.Security;

import com.byb.security.Entity.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("authCheckService")
public class AuthCheckService {

    public boolean checkPermission(HttpServletRequest request, Authentication authentication){
        Object object = authentication.getPrincipal();
        if(object instanceof UserDetails){
            SecurityUser currentUser = ((SecurityUser) object);
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(request.getRequestURI());
//            return currentUser.getAuthorities().contains(simpleGrantedAuthority);
            return currentUser.getPermissions().contains(request.getRequestURI());
        }
        return false;
    }
}
