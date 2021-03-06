package com.byb.systemservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.systemservice.Entity.Syslog;
import com.byb.systemservice.Vo.SyslogForm;

import java.util.Map;

public interface SyslogService extends IService<Syslog> {

    Long addLog(SyslogForm syslogForm);

    Map<String, Object> getSyslogList(SyslogForm syslogForm);
}
