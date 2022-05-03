package com.byb.auditservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.auditservice.Entity.Audit;
import com.byb.auditservice.Vo.AuditForm;
import com.byb.auditservice.Vo.AuditVo;

import java.util.List;
import java.util.Map;

public interface AuditService extends IService<Audit> {

    Map<String, Object> addAudit(AuditForm auditForm);

    List<AuditVo> getAuditList(AuditForm auditForm);

}
