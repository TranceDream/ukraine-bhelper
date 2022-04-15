package com.byb.security.Security;

import com.byb.BaseUtil.Config.ConstantConfig;
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
            String headUrl = request.getHeader(ConstantConfig.REQUEST_HEADER);
            String url = request.getRequestURI();
            if(url!=null&&url.equals(ConstantConfig.TOKEN_CHECK_PATH)) {
                if(headUrl!=null) {
                    return currentUser.getPermissions().contains(headUrl);
                }
            }
            return currentUser.getPermissions().contains(url);
        }
        return false;
    }
}
