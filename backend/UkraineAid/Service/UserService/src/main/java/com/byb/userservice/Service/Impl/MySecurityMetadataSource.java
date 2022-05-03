package com.byb.userservice.Service.Impl;

import cn.hutool.core.util.URLUtil;
import com.byb.userservice.Entity.Permission;
import com.byb.userservice.Dao.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component("mySecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private PermissionDao permissionDao;

    @Autowired
    public MySecurityMetadataSource(PermissionDao permissionDao){
        this.permissionDao = permissionDao;
    }

    private static Map<String, ConfigAttribute> configAttributeMap = null;

    private void loadPermission(){
        List<Permission> list = permissionDao.selectAll();
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        for (Permission resource : list) {
            map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getUrl()));
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttributeMap == null) this.loadPermission();
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        String url = ((FilterInvocation) object).getRequestUrl();
        //这里记得导入 hutool 包
        String path = URLUtil.getPath(url);
        //路径匹配器，路径匹配的工具
        PathMatcher pathMatcher = new AntPathMatcher();
        //拿到当前所有的权限
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        System.out.println("DynamicSecurityMetadataSource : " + "configAttributes=" + configAttributes.toString());
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
