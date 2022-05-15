package com.byb.userservice.Controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byb.BaseUtil.Config.ConstantConfig;
import com.byb.BaseUtil.Utils.ResponseUtil;
import com.byb.BaseUtil.Utils.Result;
import com.byb.BaseUtil.Utils.UUIDUtils;
import com.byb.openfeign.Client.SysClient;
import com.byb.openfeign.Form.FormGeneration;
import com.byb.security.Security.DefaultPasswordEncoder;
import com.byb.security.Security.TokenManager;
import com.byb.userservice.Dao.GroupDao;
import com.byb.userservice.Dao.MenuDao;
import com.byb.userservice.Dao.UserRoleDao;
import com.byb.userservice.Entity.*;
import com.byb.userservice.Service.*;
import com.byb.userservice.Vo.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private MenuDao menuDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;

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

    @Value("${spring.userService.deleteOperation}")
    private int deleteOperation;

    @PostMapping("/test")
    public Result<Map<String, Object>> test(@RequestBody UserForm userForm){
        String queue = "app";
        String message = "userservice-rabbitmq";
        amqpTemplate.convertAndSend(queue, message);
        return null;
    }

    @PostMapping("/checkToken")
    public void checkToken(HttpServletResponse response, HttpServletRequest request){
        try {
            String userId = tokenManager.getUserInfoFromToken(request.getHeader(ConstantConfig.TOKEN_HEADER));
            ResponseUtil.out(response, new Result(userId, Result.SUCCESS));
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @PostMapping("/getUserList")
    public Result<Map<String, Object>> getUserList(@RequestBody UserForm userForm, HttpServletRequest request){
        System.out.println("迪克进来了user");
        Integer pageSize = userForm.getPageSize();
        Integer pageNo = userForm.getPageNo();
        if(pageNo==null||pageNo==0){
            userForm.setPageNo(1);
        }
        if(pageSize==null||pageSize==0){
            userForm.setPageSize(10);
        }
        Map<String, Object> dataMap = new HashMap<>();
        Long loginId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        userForm.setLoginId(loginId);
        try {
            dataMap = userService.selectUserList(userForm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/getUserDetail")
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

            Map<String, Object> sysForm = FormGeneration.generateSysForm(userRoleObjtypeId, Long.valueOf(userForm.getUserRoleId()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), message, updateOperation);

//            Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
            this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/userEmpowerment")
    public Result<String> userEmpowerment(@RequestBody UserForm userForm, HttpServletRequest request, HttpServletResponse response){
        if(userForm.getRoleId() == null || userForm.getUserId() == null || userForm.getGroupId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Map<String, Object> dataMap = userService.userEmpowerment(userForm);
        boolean flag = (Boolean) dataMap.get("flag");

        if(!flag){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }
        try {
            Long objId = Long.valueOf (dataMap.get("userRoleId").toString());

            Map<String, Object> sysForm = FormGeneration.generateSysForm(userRoleObjtypeId, objId, Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "用户赋权", addOperation);

//            Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
            this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result<>("操作成功", Result.SUCCESS);
    }

    @PostMapping("/deleteUser")
    public Result<Map<String, Object>> deleteUser(@RequestBody UserForm userForm, HttpServletResponse response, HttpServletRequest request){
        if(userForm.getUserId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Map<String, Object> result = new HashMap<>();

        User user = userService.getById(userForm.getUserId());
        user.setDeleteMark("YES");
        userService.updateById(user);
        try {
            Map<String, Object> sysForm = FormGeneration.generateSysForm(userRoleObjtypeId, userForm.getUserId(), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "用户删除", updateOperation);
            this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(null, Result.FAIL, "操作失败");
        }
        return new Result<>(null, Result.SUCCESS, "操作成功");
    }

    @PostMapping("/changePwd")
    public Result<Map<String, Object>> updatePwd(@RequestBody UserForm userForm, HttpServletResponse response){

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

        if(userForm.getUserId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Boolean flag = userAuthService.updatePwd(userForm);
        if(flag){
            return new Result<>(null, Result.SUCCESS, "操作成功");
        }

        return new Result<>(null, Result.FAIL, "操作失败");
    }

    @PostMapping("/getRoleList")
    public Result<Map<String, Object>> getRoleList(@RequestBody RoleForm roleForm, HttpServletRequest request){
        if(roleForm.getPageNo()==null){
            roleForm.setPageNo(1);
        }
        if(roleForm.getPageSize()==null){
            roleForm.setPageSize(10);
        }
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        roleForm.setUserId(userId);
        Map<String, Object> dataMap = roleService.getRoleList(roleForm);
        return new Result<>(dataMap, Result.SUCCESS);
    }

    @PostMapping("/getRoleDetail")
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

        Map<String, Object> sysForm = FormGeneration.generateSysForm(roleObjtypeId, Long.valueOf((Integer)dataMap.get("roleId")), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "新增角色", addOperation);

//        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
        this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);

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

        Map<String, Object> sysForm = FormGeneration.generateSysForm(rolePermissionObjtypeId, Long.valueOf(roleForm.getRolePermissionId()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), message, updateOperation);

//        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
        this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/getPermission4Role")
    public Result<List<Permission>> getPermission4Role(@RequestBody PermissionForm permissionForm){
        List<Permission> list = permissionService.getPermission4Role(permissionForm);
        return new Result<>(list, Result.SUCCESS);
    }

    @PostMapping("/getPermissionList")
    public Result<Map<String, Object>> getPermissionList(@RequestBody PermissionForm permissionForm){
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
        if(permissionForm.getParentId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "PARENT IS EMPTY"));
        }
        try {

            Map<String, Object> dataMap = permissionService.addPermission(permissionForm);
            Boolean flag = (Boolean) dataMap.get("flag");
            if (!flag) {
                ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
            }
            Long objId = Long.valueOf(dataMap.get("permissionId").toString());
            Map<String, Object> sysForm = FormGeneration.generateSysForm(permissionObjtypeId, objId, Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "增加权限", addOperation);

//        Result<Map<String, Object>> sysResult = sysClient.addLog(sysForm);
            this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/updatePermission")
    public Result<Map<String, Object>> updatePermission(@RequestBody PermissionForm permissionForm, HttpServletResponse response, HttpServletRequest request){
        if(permissionForm.getPermissionId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        try {
            Permission permission = permissionService.getById(permissionForm.getPermissionId());
            if(permissionForm.getPermissionName()!=null) {
                permission.setPermissionName(permissionForm.getPermissionName());
            }
            if(permissionForm.getIcon()!=null) {
                permission.setIcon(permissionForm.getIcon());
            }
            if(permissionForm.getUrl()!=null) {
                permission.setUrl(permissionForm.getUrl());
            }
            if(permissionForm.getParentId()!=null) {
                permission.setParentId(permissionForm.getParentId());
            }
            if(permissionForm.getDeleteMark()!=null)
            if(permissionForm.getDeleteMark().equals("YES") || permissionForm.getDeleteMark().equals("NO")) {
                permission.setDeleteMark(permissionForm.getDeleteMark());
            }

            permissionService.updateById(permission);

            Map<String, Object> sysForm = FormGeneration.generateSysForm(permissionObjtypeId, Long.valueOf(permissionForm.getPermissionId().toString()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "修改权限", updateOperation);
            this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/deletePermission")
    public Result<Map<String, Object>> deletePermission(@RequestBody PermissionForm permissionForm, HttpServletResponse response, HttpServletRequest request){
        if(permissionForm.getPermissionId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        Permission permission = permissionService.getById(permissionForm.getPermissionId());

        permission.setDeleteMark("YES");
        permissionService.updateById(permission);

        Map<String, Object> sysForm = FormGeneration.generateSysForm(permissionObjtypeId, Long.valueOf(permissionForm.getPermissionId().toString()), Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER)), "删除权限", deleteOperation);
        this.sendMessage(ConstantConfig.SYSL0G_QUEUE, sysForm);

        return new Result(Result.SUCCESS, "操作成功");
    }

    @PostMapping("/getEmail")
    public Result<List<String>> getEmail(@RequestBody List<Long> userIds){
        if(userIds == null || userIds.isEmpty()){
            return new Result<>(new ArrayList<>(), Result.FAIL);
        }

        Map<String, Object> dataMap = userService.getEmail(userIds);
        List<String> emails = (List<String>) dataMap.get("data");
        return new Result<>(emails, Result.SUCCESS);
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

    @PostMapping("/identify")
    public Result<Map<String, Object>> identify(@RequestBody UserForm userForm){

        Map<String, Object> result = new HashMap<>();
        if(userForm.getName() == null){
            return new Result<>(null, Result.FAIL, "姓名为空");
        }

        if(userForm.getIdentityNo() == null){
            return new Result<>(null, Result.FAIL, "身份证为空");
        }

        if(userForm.getUserId() == null){
            return new Result<>(null, Result.FAIL, "用户Id为空");
        }

        Boolean flag = userService.identify(userForm);
        if(!flag){
            return new Result<>(null, Result.SUCCESS, "实名认证成功");
        }
        return new Result<>(null, Result.FAIL, "实名认证失败");
    }

    @PostMapping("/updateRole")
    public Result<Map<String, Object>> updateRole(@RequestBody RoleForm roleForm, HttpServletResponse response, HttpServletRequest request){
        if(roleForm.getRoleId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }

        try {
            Role role = roleService.getById(roleForm.getRoleId());
            BeanUtils.copyProperties(roleForm, role);
            roleService.updateById(role);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        Map<String, Object> syslogForm = FormGeneration.generateSysForm(roleObjtypeId, Long.valueOf(roleForm.getRoleId()), userId, "修改角色", updateOperation);

        this.sendMessage(ConstantConfig.SYSL0G_QUEUE, syslogForm);
        return new Result<>(null, Result.SUCCESS);

    }

    @PostMapping("/deleteRole")
    public Result<Map<String, Object>> deleteRole(@RequestBody RoleForm roleForm, HttpServletResponse response, HttpServletRequest request){
        if(roleForm.getRoleId() == null){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "ID IS EMPTY"));
        }
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        roleForm.setUserId(userId);

        Boolean flag = roleService.deleteRole(roleForm);

        if(!flag){
            ResponseUtil.out(response, new Result(null, Result.FAIL, "操作失败"));
        }

        Map<String, Object> syslogForm = FormGeneration.generateSysForm(roleObjtypeId, Long.valueOf(roleForm.getRoleId()), userId, "删除角色", updateOperation);

        this.sendMessage(ConstantConfig.SYSL0G_QUEUE, syslogForm);
        return new Result<>(null, Result.SUCCESS);
    }


    @PostMapping("/getModuleList")
    public Result<List<ModuleVo>> getModuleList(HttpServletRequest request){

        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        List<ModuleVo> list = userService.getModuleList(userId);

        return new Result<>(list, Result.SUCCESS);
    }

    @PostMapping("/getMenuList")
    public Result<List<Menu>> getMenuList(){

        List<Menu> menus = menuDao.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getDeleteMark,"NO"));
        return new Result<>(menus, Result.SUCCESS);

    }

//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response){
//
//        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
//        redisTemplate.opsForValue().getAndDelete(userId);
//        ResponseUtil.out(response, new Result(null, Result.SUCCESS, "登出成功"));
//    }

    @PostMapping("/getChildGroupsSql")
    public String getChildGroupsSql(@RequestParam("userId") Long userId){
        String result = userService.getOwnAndChildGroups(userId);
        return result;
    }

    @PostMapping("/getChildGroupVos")
    public Result<Map<String, Object>> getChildGroupVos(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader(ConstantConfig.LOGIN_USER_HEADER));
        List<GroupForm> list = userService.getGroupList(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/getOneGroup")
    public Result<Map<String, Object>> getOneGroup(@RequestBody Map<String, Object> groupMap){
        Long userId = Long.valueOf(groupMap.get("userId").toString());
        Integer roleId = (Integer) groupMap.get("roleId");
        if(userId == null || roleId == null){
            return new Result(null, Result.FAIL, "数据不全");
        }
        GroupForm groupForm = userService.getOneGroup(userId, roleId);
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupForm.getGroupId());
        map.put("groupName", groupForm.getGroupName());
        return new Result<>(map, Result.SUCCESS);
    }

    @PostMapping("/getChildGroup")
    public Result<Map<Integer, String>> getChildGroup(@RequestParam("groupId") Integer groupId){
        List<GroupForm> list = userService.getChildGroup(groupId);
        Map<Integer, String> result = new HashMap<>();
        for(GroupForm groupForm : list){
            result.put(groupForm.getGroupId(), groupForm.getGroupName());
        }
        return new Result<>(result, Result.SUCCESS);
    }

    private void sendMessage(String queue, Object object){
        String msg = JSONObject.toJSONString(object);
        amqpTemplate.convertAndSend(queue, msg);
    }

}
