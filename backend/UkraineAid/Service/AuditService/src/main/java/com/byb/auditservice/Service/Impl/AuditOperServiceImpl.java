package com.byb.auditservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.auditservice.Dao.AuditOperDao;
import com.byb.auditservice.Entity.AuditOper;
import com.byb.auditservice.Service.AuditOperService;
import org.springframework.stereotype.Service;

@Service
public class AuditOperServiceImpl extends ServiceImpl<AuditOperDao, AuditOper> implements AuditOperService {
}
