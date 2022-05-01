package com.byb.reportservice.Controller;

import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Form.FormGeneration;
import com.byb.reportservice.Service.ReportService;
import com.byb.reportservice.Vo.ReportForm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Value("${spring.reportService.reportObjtypeId}")
    private Integer reportObjtypeId;

    @Autowired
    private ReportService reportService;

    @PostMapping("/addReport")
    public Result<Map<String, Object>> addReport(@RequestBody ReportForm reportForm, HttpServletResponse response, HttpServletRequest request){

        if(reportForm.getDefense() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报对象为空"));
        }

        if(reportForm.getReason() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报理由为空"));
        }

        if(reportForm.getObjtypeId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报类型为空"));
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        reportForm.setProsecution(userId);

        Map<String, Object> dataMap = reportService.addReport(reportForm);
        Boolean flag = (Boolean) dataMap.get("flag");
        if(!flag){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "举报失败"));
        }

        return new Result<>(dataMap, Result.SUCCESS, "举报成功");
    }

    @GetMapping("/getReportList")
    public Result<Map<String, Object>> getReportList(@RequestBody ReportForm reportForm, HttpServletResponse response){

        if(reportForm.getObjtypeId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "类型为空"));
        }

        if(reportForm.getPageNo() == null){
            reportForm.setPageNo(1);
        }

        if(reportForm.getPageSize() == null){
            reportForm.setPageSize(10);
        }

        Map<String, Object> dataMap = reportService.getReportList(reportForm);

        return new Result<>(dataMap, Result.SUCCESS);
    }

    @GetMapping("/getReportDetail")
    public Result<Map<String, Object>> getReportDetail(@RequestBody ReportForm reportForm, HttpServletResponse response){

        if(reportForm.getObjtypeId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "类型为空"));
        }

        if(reportForm.getDefense() == null) {
            ResponseUtil.out(response, new Result(null, Result.FAIL, "被举报对象为空"));
        }

        if(reportForm.getPageNo() == null){
            reportForm.setPageNo(1);
        }

        if(reportForm.getPageSize() == null){
            reportForm.setPageSize(10);
        }

        Map<String, Object> dataMap = reportService.getReportDetail(reportForm);

        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/doAudit")
    public Result<Map<String, Object>> doAudit(@RequestBody ReportForm reportForm, HttpServletResponse response, HttpServletRequest request){

        if(reportForm.getObjtypeId() == null || reportForm.getDefense()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核信息不全"));
        }

        return null;
    }

}
