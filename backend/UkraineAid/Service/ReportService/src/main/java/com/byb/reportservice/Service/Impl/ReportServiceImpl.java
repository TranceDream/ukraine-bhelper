package com.byb.reportservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.reportservice.Dao.ReportDao;
import com.byb.reportservice.Entity.Report;
import com.byb.reportservice.Service.ReportService;
import com.byb.reportservice.Vo.ReportForm;
import com.byb.reportservice.Vo.ReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl extends ServiceImpl<ReportDao, Report> implements ReportService {

    @Override
    public Map<String, Object> addReport(ReportForm reportForm) {
        Map<String, Object> result = new HashMap<>();
        Report report = new Report();
        BeanUtils.copyProperties(reportForm, report);
        try {
            baseMapper.insert(report);
            Integer reportId = report.getReportId();
            result.put("flag", true);
            result.put("reportId", reportId);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("flag", false);
            return result;
        }
    }

    @Override
    public Map<String, Object> getReportList(ReportForm reportForm) {

        Map<String, Object> params = new HashMap<>();
        params.put("start", (reportForm.getPageNo()-1) * reportForm.getPageSize());
        params.put("size", reportForm.getPageSize());
        params.put("objtypeId", reportForm.getObjtypeId());

        Map<String, Object> result = new HashMap<>();

        List<ReportVo> list = new ArrayList<>();

        Integer total = baseMapper.countReportList(params);
        if(total > 0) {
            list = baseMapper.getReportList(params);
        }

        result.put("total", total);
        result.put("data", list);
        result.put("pageNo", reportForm.getPageNo());
        result.put("pageSize", reportForm.getPageSize());

        return result;
    }

    @Override
    public Map<String, Object> getReportDetail(ReportForm reportForm) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", (reportForm.getPageNo() - 1) * reportForm.getPageSize());
        params.put("size", reportForm.getPageSize());
        params.put("objtypeId", reportForm.getObjtypeId());
        params.put("objId", reportForm.getDefense());

        Map<String, Object> result = new HashMap<>();

        List<ReportVo> list = new ArrayList<>();

        Integer total = baseMapper.countReportDetail(params);
        if(total > 0){
            list = baseMapper.getReportList(params);
        }

        result.put("total", total);
        result.put("data", list);
        result.put("pageNo", reportForm.getPageNo());
        result.put("pageSize", reportForm.getPageSize());

        return result;
    }

}
