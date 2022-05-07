package ServiceTest;

import com.byb.userservice.Service.PermissionService;
import com.byb.userservice.UserServiceApplication;
import com.byb.userservice.Vo.PermissionForm;
import com.byb.userservice.Vo.PermissionVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {UserServiceApplication.class})
public class PermissionServiceTest {

    @Autowired
    private PermissionService permissionService;

    @Test
    void getPermissionList(){
        PermissionForm permissionForm = new PermissionForm();
        Map<String, Object> result = permissionService.getPermissionList(permissionForm);
        List<PermissionVo> list = (List<PermissionVo>) result.get("data");
        Assertions.assertNotNull(list);
        permissionForm.setPageSize(3);
        result = permissionService.getPermissionList(permissionForm);
        list = (List<PermissionVo>) result.get("data");
        Assertions.assertEquals(3, list.size());
        permissionForm.setPermissionId(10000);
        Assertions.assertEquals(1, (int) permissionService.getPermissionList(permissionForm).get("total"));
    }

    @Test
    void addPermission(){
        PermissionForm permissionForm = new PermissionForm();
        permissionForm.setPermissionName("测试");
        permissionForm.setUrl("/user/test");
        Boolean flag = (Boolean) permissionService.addPermission(permissionForm).get("flag");
        Assertions.assertEquals(true, flag);
        permissionForm = new PermissionForm();
        permissionForm.setUrl("/user/test");
        flag = (Boolean) permissionService.addPermission(permissionForm).get("flag");
        Assertions.assertEquals(false, flag);
        permissionForm = new PermissionForm();
        permissionForm.setPermissionName("测试");
        flag = (Boolean) permissionService.addPermission(permissionForm).get("flag");
        Assertions.assertEquals(false, flag);
    }

}
