package com.byb.userservice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.BaseUtil.Utils.UUIDUtils;
import com.byb.openfeign.Client.SysClient;
import com.byb.security.Security.TokenManager;
import com.byb.userservice.Entity.User;
import com.byb.userservice.Service.*;
import com.byb.userservice.Vo.PermissionForm;
import com.byb.userservice.Vo.RoleForm;
import com.byb.userservice.Vo.UserForm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private SysClient sysClient;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Value("${spring.userService.permissionObjtypeId}")
    private int permissionObjtypeId;

    @Value("${spring.userService.roleObjtypeId}")
    private int roleObjtypeId;

    @Value("${spring.userService.userObjtypeId}")
    private int userObjtypeId;

    @Value("${spring.userService.userRoleObjtypeId}")
    private int userRoleObjtypeId;

    @Value("${spring.userService.rolePermissionObjtypeId}")
    private int rolePermissionObjtypeId;

    @Value("${spring.userService.addOperation}")
    private int addOperation;

    @Value("${spring.userService.updateOperation}")
    private int updateOperation;

    @PostMapping("/test")
    public Result<Map<String, Object>> test(@RequestBody UserForm userForm){
        Long l = userForm.getUserId();
        System.out.println(l);
        return null;
    }

    @PostMapping("/checkToken")
    public void checkToken(HttpServletResponse response, HttpServletRequest request){
        String userId = tokenManager.getUserInfoFromToken(request.getHeader(ConstantConfig.TOKEN_HEADER));
        ResponseUtil.out(response, new Result(userId, Result.SUCCESS));
    }

    @PostMapping("/addUserByEmail")
    public Result<Map<String, Object>> addByEmail(@RequestBody UserForm userForm, HttpServletResponse response){
        if(userForm.getIdentifier()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写邮箱地址"));
        }

        String credential = userForm.getCredential();
        if(credential==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码不能为空"));
        }

        if(credential.length()<8){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码长度至少8位"));
        }

        if(credential.length()>20){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码长度不超过20位"));
        }

        String letter = ".*[a-zA-Z]+.*";
        String digit = ".*[0-9]+.*";
        Matcher m = Pattern.compile(letter).matcher(credential);
        if(!m.matches()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码需要包含大小写字母"));
        }

        m =  Pattern.compile(digit).matcher(credential);
        if(!m.matches()){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "密码需要包含数字"));
        }

        if(userForm.getCountry()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的国籍"));
        }

        if(userForm.getCity()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的城市"));
        }

        Boolean isExist = userAuthService.checkAccount(userForm);
        if(isExist){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "用户已存在"));
        }

        String uuid = UUIDUtils.createUUID();
        redisTemplate.opsForValue().set(userForm.getIdentifier(), uuid,18000, TimeUnit.SECONDS);
        userForm.setUuid(uuid);
        Boolean isSend = emailService.sendHtml(userForm);
        if(!isSend){
            redisTemplate.opsForHash().delete(userForm.getIdentifier());
            ResponseUtil.out(response, new Result(null, Result.FAIL, "邮件发送失败，请检查邮箱格式，或稍后重试"));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("邮箱", userForm.getIdentifier());
        return new Result<>(map, Result.SUCCESS, "请前往邮箱激活账户");
    }

    @GetMapping("/activeEmail")
    public Result<Map<String, Object>> activeEmail(@RequestParam("token") String token,  HttpServletResponse response, HttpServletRequest request){

        UserForm userForm = new UserForm();

        try {
            String userinfo = tokenManager.getUserInfoFromToken(token);
            userForm = JSONObject.parseObject(userinfo, UserForm.class);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtil.out(response, new Result(null, Result.FAIL, "凭证有误"));
        }

        if(userForm.getCountry()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的国籍"));
        }

        if(userForm.getCity()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "请填写您的城市"));
        }

        String uuid = userForm.getUuid();
        String redisUUID = (String) redisTemplate.opsForValue().get(userForm.getIdentifier());
        if(redisUUID == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "连接超时，请重新注册"));
        }
        else if(!redisUUID.equals(uuid)){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "认证失败"));
        }
        else{
            Long userId = userService.createAccount(userForm);
            userForm.setUserId(userId);
            userAuthService.createAccount(userForm);
            Map<String, Object> map = new HashMap<>();
            map.put("userId", userId);
            return new Result<>(map, Result.SUCCESS, "认证成功");
        }
        return null;
    }

    @GetMapping("/getUserList")
    public Result<Map<String, Object>> getUserList(@RequestBody UserForm userForm){
        Integer pageSize = userForm.getPageSize();
        Integer pageNo = userForm.getPageNo();
        if(pageNo==null||pageNo==0){
            userForm.setPageNo(1);
        }
        if(pageSize==null||pageSize==0){
            userForm.setPageSize(10);
        }
        Map<String, Object> dataMap = new HashMap<>();
        try {
            dataMap = userService.selectUserList(userForm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @GetMapping("/getUserDetail")
    public Result<Map<String, Object>> getUserDetail(@RequestBody UserForm userForm, HttpServletResponse response){
        if(userForm.getUserId()==null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        Map<String, Object> dataMap = new HashMap<>();
        try {
            dataMap = userService.getUserDetail(userForm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/updateUser")
    public Result<Map<String, Object>> updateUser(@RequestBody UserForm userForm, HttpServletResponse response) {
        if(userForm.getUserId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.updateById(user);
        Map<String, Object> result = new HashMap<>();
        result.put("data", userForm);
        return new Result<>(result, Result.SUCCESS);
    }

    @PostMapping("/manageRole")
    public Result manageRole(@RequestBody UserForm userForm, HttpServletResponse response, HttpServletRequest request){
        if(userForm.getUserRoleId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        try {


            String message = "";
            if (userForm.getLockedMark() != null && userForm.getLockedMark().equals("NO")) {
                message = "恢复用户权限";
            } else if (userForm.getLockedMark() != null && userForm.getLockedMark().equals("YES")) {
                message = "剥夺用户权限";
            } else {
                ResponseUtil.out(response, new Result(null, Result.FAIL, "指令错误"));
            }
            Map<String, Object> operResult = userService.manageRole(userForm);
            if (!(Boolean) operResult.get("flag")) {
                ResponseUtil.out(response, new Result(null, Result.FAIL, String.valueOf(operResult.get("msg"))));
            }

            Map<String, Object> sysForm = this.generateSysForm(userRoleObjtypeId, Long.valueOf(userForm.getUserRoleId()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), message, updateOperation);

        /*
        TODO: 写日志操作后期需要换成异步执行，使用消息队列
         */
            Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/userEmpowerment")
    public Result<String> userEmpowerment(@RequestBody UserForm userForm, HttpServletRequest request, HttpServletResponse response){
        if(userForm.getRoleId() == null || userForm.getUserId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Map<String, Object> dataMap = userService.userEmpowerment(userForm);
        boolean flag = (Boolean) dataMap.get("flag");

        if(!flag){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }
        try {
            Long objId = Long.valueOf (dataMap.get("userRoleId").toString());

            Map<String, Object> sysForm = this.generateSysForm(userRoleObjtypeId, objId, Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "用户赋权", addOperation);

         /*
        TODO: 写日志操作后期需要换成异步执行，使用消息队列
         */
            Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result<>("操作成功", Result.SUCCESS);
    }

    @GetMapping("/getRoleList")
    public Result<Map<String, Object>> getRoleList(@RequestBody RoleForm roleForm){
        if(roleForm.getPageNo()==null){
            roleForm.setPageNo(1);
        }
        if(roleForm.getPageSize()==null){
            roleForm.setPageSize(10);
        }
        Map<String, Object> dataMap = roleService.getRoleList(roleForm);
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @GetMapping("/getRoleDetail")
    public Result<Map<String, Object>> getRoleDetail(@RequestBody RoleForm roleForm, HttpServletResponse response){
        if( Integer.valueOf(roleForm.getRoleId()) == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Map<String, Object> dataMap = roleService.getRoleDetail(roleForm);
        if(dataMap == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "查询信息错误"));
        }

        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/addRole")
    public Result<String> addRole(@RequestBody RoleForm roleForm, HttpServletResponse response, HttpServletRequest request){
        if(roleForm.getRoleName() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "NAME IS EMPTY"));
        }

        Map<String, Object> dataMap =  roleService.addRole(roleForm);

        if(!(Boolean) dataMap.get("flag")){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }

        Map<String, Object> sysForm = this.generateSysForm(roleObjtypeId, Long.valueOf((Integer)dataMap.get("roleId")), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "新增角色", addOperation);

         /*
        TODO: 写日志操作后期需要换成异步执行，使用消息队列
         */
        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);

        return new Result<>("操作成功", Result.SUCCESS);
    }

    @PostMapping("/managePermission")
    public Result<Map<String, Object>> managePermission(@RequestBody RoleForm roleForm, HttpServletResponse response, HttpServletRequest request){
        if(roleForm.getRolePermissionId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        String message = "";
        if(roleForm.getLockedMark()!=null && roleForm.getLockedMark().equals("NO")){
            message = "恢复角色权限";
        }
        else if(roleForm.getLockedMark().equals("YES")){
            message = "剥夺角色权限";
        }
        else{
            ResponseUtil.out(response, new Result(null, Result.FAIL, "指令错误"));
        }
        Map<String, Object> operResult = roleService.managePermission(roleForm);
        if(!(Boolean) operResult.get("flag")){
            ResponseUtil.out(response, new Result(null, Result.FAIL, String.valueOf(operResult.get("msg"))));
        }

        Map<String, Object> sysForm = this.generateSysForm(rolePermissionObjtypeId, Long.valueOf(roleForm.getRolePermissionId()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), message, updateOperation);

        /*
        TODO: 写日志操作后期需要换成异步执行，使用消息队列
         */
        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);

        return new Result(Result.SUCCESS, "操作成功");
    }

    @GetMapping("/getPermissionList")
    public Result<Map<String, Object>> getPermissionList(@RequestBody PermissionForm permissionForm){

        if(permissionForm.getPageNo()==null){
            permissionForm.setPageNo(1);
        }

        if(permissionForm.getPageSize()==null){
            permissionForm.setPageSize(10);
        }

        Map<String, Object> dataMap = permissionService.getPermissionList(permissionForm);
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/addPermission")
    public Result<Map<String, Object>> addPermission(@RequestBody PermissionForm permissionForm, HttpServletResponse response, HttpServletRequest request){
        if(permissionForm.getUrl() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "URL IS EMPTY"));
        }
        if(permissionForm.getPermissionName() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "NAME IS EMPTY"));
        }

        Map<String, Object> dataMap = permissionService.addPermission(permissionForm);
        Boolean flag = (Boolean) dataMap.get("flag");
        if(!flag){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }
        Long objId = Long.valueOf((Integer)dataMap.get("permissionId"));
        Map<String, Object> sysForm = this.generateSysForm(permissionObjtypeId, objId, Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "增加权限", addOperation);

        /*
        TODO: 写日志操作后期需要换成异步执行，使用消息队列
         */
        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/emailtest")
    public String emailtest(){
        emailService.sendEmail("1.com.com.com.com", "ddd");
        return "dddd";
    }

    @PostMapping("/htmlEmail")
    public String ttt(@RequestBody UserForm userForm) {
//        emailService.sendHtml(userForm.getIdentifier());
        return "ttt";
    }

    private Map<String, Object> generateSysForm(int objtypeId, Long objId, Long operator, String message, int operation){
        Map<String, Object> sysForm = new HashMap<>();
        sysForm.put("objtypeId", objtypeId);
        sysForm.put("message", message);
        sysForm.put("objId", objId);
        sysForm.put("operator", operator);
        sysForm.put("operation", operation);
        return sysForm;
    }

}
