package com.byb.auditservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.auditservice.Dao.StatusDao;
import com.byb.auditservice.Entity.Status;
import com.byb.auditservice.Service.StatusService;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl extends ServiceImpl<StatusDao, Status> implements StatusService {
}
