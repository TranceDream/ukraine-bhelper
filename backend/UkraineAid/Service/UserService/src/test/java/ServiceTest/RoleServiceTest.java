package ServiceTest;

import com.byb.userservice.Service.RoleService;
import com.byb.userservice.UserServiceApplication;
import com.byb.userservice.Vo.RoleForm;
import com.byb.userservice.Vo.RoleVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {UserServiceApplication.class})
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void getRoleList(){
        RoleForm roleForm = new RoleForm();
        List<RoleVo> list = (List<RoleVo>) roleService.getRoleList(roleForm).get("data");
        Assertions.assertNotNull(list);
        roleForm.setRoleId(10000);
        Assertions.assertEquals(1, (int)roleService.getRoleList(roleForm).get("total"));
        roleForm = new RoleForm();
        roleForm.setPageSize(2);
        Assertions.assertEquals(2, ((List<RoleVo>) roleService.getRoleList(roleForm)).size());
    }

    @Test
    void getRoleDetail(){
        RoleForm roleForm = new RoleForm();
        roleForm.setRoleId(10001);
        Map<String, Object> result = roleService.getRoleDetail(roleForm);
        RoleVo roleVo = (RoleVo) result.get("data");
        Assertions.assertEquals(10001, roleVo.getRoleId());
    }

    @Test
    void managePermission(){
        RoleForm roleForm = new RoleForm();
        roleForm.setRolePermissionId(10003);
        roleForm.setLockedMark("NO");
        Boolean flag = (Boolean) roleService.managePermission(roleForm).get("flag");
        Assertions.assertEquals(true, flag);
        roleForm = new RoleForm();
        roleForm.setLockedMark("NO");
        flag = (Boolean) roleService.managePermission(roleForm).get("flag");
        Assertions.assertEquals(false, flag);
        roleForm = new RoleForm();
        roleForm.setRolePermissionId(10003);
        flag = (Boolean) roleService.managePermission(roleForm).get("flag");
        Assertions.assertEquals(false, flag);
    }

    @Test
    void addRole(){
        RoleForm roleForm = new RoleForm();
        Boolean flag = (Boolean) roleService.addRole(roleForm).get("flag");
        Assertions.assertEquals(false, flag);
        roleForm.setRoleName("测试");
        Map<String, Object> result = roleService.addRole(roleForm);
        flag = (Boolean) result.get("flag");
        Assertions.assertEquals(true, flag);
        Assertions.assertNotNull(result.get("roleId"));
        roleForm = new RoleForm();
        roleForm.setRoleName("测试");
        List<Integer> list = new ArrayList<>();
        list.add(10000);
        list.add(10001);
        roleForm.setPermissions(list);
        flag = (Boolean) roleService.addRole(roleForm).get("flag");
        Assertions.assertEquals(true, flag);
    }

}
