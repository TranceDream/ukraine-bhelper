package com.byb.userservice.Service;

import com.byb.userservice.Vo.UserForm;

public interface EmailService {

    Boolean sendEmail(String to, String text);

    Boolean sendHtml(UserForm userForm);

}
