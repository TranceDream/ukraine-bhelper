package com.byb.auditservice.Controller;


import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.auditservice.Service.AuditService;
import com.byb.auditservice.Vo.AuditForm;
import com.byb.auditservice.Vo.AuditVo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @PostMapping("/addAudit")
    public Result<Map<String, Object>> addAudit(@RequestBody AuditForm auditForm, HttpServletResponse response, HttpServletRequest request){

        if(auditForm.getObjtypeId() == null || auditForm.getOper() == null || auditForm.getObjId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "审核信息不全"));
        }

        Long operator = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        auditForm.setOperator(operator);

        Map<String, Object> dataMap = auditService.addAudit(auditForm);
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @GetMapping("/getAuditList")
    public Result<List<AuditVo>> getAuditList(@RequestBody AuditForm auditForm){

        List<AuditVo> list = auditService.getAuditList(auditForm);

        return new Result<>(list, Result.SUCCESS);
    }


}
