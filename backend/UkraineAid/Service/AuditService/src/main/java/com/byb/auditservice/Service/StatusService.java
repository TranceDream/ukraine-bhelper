package com.byb.auditservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.auditservice.Entity.Status;

import java.util.List;

public interface StatusService extends IService<Status> {

    List<Status> getStatusList();

}
