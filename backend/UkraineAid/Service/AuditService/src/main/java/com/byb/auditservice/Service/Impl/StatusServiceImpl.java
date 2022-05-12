package com.byb.auditservice.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.auditservice.Dao.StatusDao;
import com.byb.auditservice.Entity.Status;
import com.byb.auditservice.Service.StatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl extends ServiceImpl<StatusDao, Status> implements StatusService {

    @Override
    public List<Status> getStatusList() {
        List<Status> list = baseMapper.selectList(new QueryWrapper<Status>().lambda());
        return list;
    }
}
