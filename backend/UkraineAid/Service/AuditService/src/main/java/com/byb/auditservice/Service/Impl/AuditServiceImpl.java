package com.byb.auditservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.auditservice.Dao.AuditDao;
import com.byb.auditservice.Entity.Audit;
import com.byb.auditservice.Service.AuditService;
import com.byb.auditservice.Vo.AuditForm;
import com.byb.auditservice.Vo.AuditVo;
import org.bouncycastle.LICENSE;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditServiceImpl extends ServiceImpl<AuditDao, Audit> implements AuditService {

    @Override
    public Map<String, Object> addAudit(AuditForm auditForm) {
        Map<String, Object> result = new HashMap<>();
        try {
            Audit audit = new Audit();
            BeanUtils.copyProperties(auditForm, audit);
            baseMapper.insert(audit);

            Integer auditId = audit.getAuditId();

            result.put("data", auditId);
            result.put("msg", "审核成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("msg", "审核失败");
        return result;
    }

    @Override
    public List<AuditVo> getAuditList(AuditForm auditForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("objtypeId", auditForm.getObjtypeId());
        params.put("objId", auditForm.getObjId());
        List<AuditVo> list = new ArrayList<>();
        list = baseMapper.selectAuditList(params);
        return list;
    }

}
