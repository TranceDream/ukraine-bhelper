package com.byb.openfeign.Form;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormGeneration {

    public static Map<String, Object> generateSysForm(Integer objtypeId, Long objId, Long operator, String message, Integer operation){
        Map<String, Object> sysForm = new HashMap<>();
        sysForm.put("objtypeId", objtypeId);
        sysForm.put("message", message);
        sysForm.put("objId", objId);
        sysForm.put("operator", operator);
        sysForm.put("operation", operation);
        return sysForm;
    }

    public static Map<String, Object> generateReportForm(Integer objtypeId, Long denfense, Long prosecution, String reason, Integer pageSize, Integer pageNo){
        Map<String, Object> reportForm = new HashMap<>();
        reportForm.put("objtypeId", objtypeId);
        reportForm.put("defense", denfense);
        reportForm.put("prosecution", prosecution);
        reportForm.put("reason", reason);
        reportForm.put("pageNo", pageNo);
        reportForm.put("pageSize", pageSize);
        return reportForm;
    }

    public static Map<String, Object> generateAuditForm(Integer objtypeId, Long objId, Long operator, Integer oper, String message, Integer pageNo, Integer pageSize){
        Map<String, Object> auditForm = new HashMap<>();
        auditForm.put("objtypeId", objtypeId);
        auditForm.put("objId", objId);
        auditForm.put("operator", operator);
        auditForm.put("oper", oper);
        auditForm.put("message", message);
        auditForm.put("pageNo", pageNo);
        auditForm.put("pageSize", pageSize);
        return auditForm;
    }

    public static Map<String, Object> generateMessageForm(String content, String[] emails, List<Long> specificUsers, String title){
        Map<String, Object> messageForm = new HashMap<>();
        messageForm.put("content", content);
        messageForm.put("emails", emails);
        messageForm.put("specificUsers", specificUsers);
        messageForm.put("title", title);
        return messageForm;
    }

    public static Map<String, Object> generateUserForm(Long userId, String name, String identityNo, Integer roleId, String ifverified){
        Map<String, Object> userForm = new HashMap<>();
        userForm.put("userId", userId);
        userForm.put("name", name);
        userForm.put("identityNo", identityNo);
        userForm.put("roleId", roleId);
        userForm.put("ifverified", ifverified);
        return userForm;
    }

}
