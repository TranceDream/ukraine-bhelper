package com.byb.systemservice.Service;

import com.byb.systemservice.Vo.MessageForm;

public interface EmailService {

    Boolean sendEmail(String[] to, String text);

}
