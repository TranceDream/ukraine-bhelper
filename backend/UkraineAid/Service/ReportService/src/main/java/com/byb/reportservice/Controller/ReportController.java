package com.byb.reportservice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.openfeign.Client.AuditClient;
import com.byb.openfeign.Client.SysClient;
import com.byb.openfeign.Client.UserClient;
import com.byb.openfeign.Form.FormGeneration;
import com.byb.reportservice.Entity.Report;
import com.byb.reportservice.Service.ReportService;
import com.byb.reportservice.Vo.ReportForm;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Value("${spring.reportService.reportObjtypeId}")
    private Integer reportObjtypeId;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AuditClient auditClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/addReport")
    public Result<Map<String, Object>> addReport(@RequestBody ReportForm reportForm, HttpServletResponse response, HttpServletRequest request){

        if(reportForm.getDefense() == null){
            return new Result(null, Result.FAIL, "举报对象为空");
        }

        if(reportForm.getReason() == null){
            return new Result(null, Result.FAIL, "举报理由为空");
        }

        if(reportForm.getObjtypeId() == null){
            return  new Result(null, Result.FAIL, "举报类型为空");
        }

//        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
//        reportForm.setProsecution(userId);

        Map<String, Object> dataMap = reportService.addReport(reportForm);
        Boolean flag = (Boolean) dataMap.get("flag");
        if(!flag){
            return new Result(null, Result.FAIL, "举报失败");
        }

        return new Result<>(dataMap, Result.SUCCESS, "举报成功");
    }

    @PostMapping("/getReportList")
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

    @PostMapping("/getReportDetail")
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

        if(reportForm.getDefense()==null || reportForm.getOper()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核信息不全"));
        }

        int oper = 0;
        String msg = "";
        if(reportForm.getOper().equals("通过")){
            oper = 10000;
            msg = "举报审核通过";
        }else if(reportForm.getOper().equals("驳回")){
            oper = 10001;
            msg = "举报审核驳回";
        }else {
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核信息错误"));
        }

        Map<String, Object> dataMap = reportService.doAudit(reportForm);
        Boolean flag = (Boolean) dataMap.get("flag");
        List<Report> list = (List<Report>) dataMap.get("data");
        List<Long> userIds = new ArrayList<>();

        if(flag){
            Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
            Map<String, Object> auditForm = FormGeneration.generateAuditForm(reportObjtypeId, Long.valueOf(reportForm.getDefense().toString()), userId, oper, msg, null, null);
            auditClient.addAudit(auditForm);
            for(Report report : list){
                userIds.add(report.getProsecution());
            }
            List<String> emails = userClient.getEmail(userIds).getData();
            if(reportForm.getOper().equals("通过") && emails.size()>0){
                String[] emailstrs = new String[emails.size()];
                for (int i = 0; i < emails.size(); i++) {
                    emailstrs[i] = emails.get(i);
                }
                Map<String, Object> messageForm = FormGeneration.generateMessageForm("您的举报审核通过，感谢您对社区环境做出的贡献",emailstrs, userIds, "举报结果");
                this.sendMessage(ConstantConfig.MESSAGE_QUEUE, messageForm);
            }
            return new Result<>(null, Result.SUCCESS, "操作成功");
        }else{
            return new Result<>(null, Result.FAIL, "操作失败");
        }
    }

    @PostMapping("/getObjtypeList")
    public Result<Map<Integer, String>> getObjtypeList(){
        List<Integer> list = reportService.getObjtypeId();
        Map<Integer, String> result = sysClient.getObjtypeList(list);
        return new Result<>(result, Result.SUCCESS);
    }

    private void sendMessage(String queue, Object object){
        String msg = JSONObject.toJSONString(object);
        amqpTemplate.convertAndSend(queue, msg);
    }

}
