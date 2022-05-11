package com.byb.reportservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.reportservice.Entity.Report;
import com.byb.reportservice.Vo.ReportForm;

import java.util.Map;

public interface ReportService extends IService<Report> {

    Map<String, Object> addReport(ReportForm reportForm);

    Map<String, Object> getReportList(ReportForm reportForm);

    Map<String, Object> getReportDetail(ReportForm reportForm);

    Map<String, Object> doAudit(ReportForm reportForm);

}
