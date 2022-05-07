package ServiceTest;

import com.byb.userservice.Service.UserService;
import com.byb.userservice.UserServiceApplication;
import com.byb.userservice.Vo.UserForm;
import com.byb.userservice.Vo.UserVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {UserServiceApplication.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void selectUserList(){
        UserForm userForm = new UserForm();
        Assertions.assertNotNull(userService.selectUserList(userForm).get("data"));
        userForm.setUserId(10000l);
        Assertions.assertEquals(1, (int)userService.selectUserList(userForm).get("total"));
        userForm = new UserForm();
        userForm.setPageNo(1);
        userForm.setPageSize(2);
        Assertions.assertEquals(2, ((List<UserVo>)userService.selectUserList(userForm).get("data")).size());
    }

    @Test
    void getUserDetail(){
        UserForm userForm = new UserForm();
        userForm.setUserId(10000l);
        Map<String, Object> result = userService.getUserDetail(userForm);
        UserVo userVo = (UserVo) result.get("data");
        Assertions.assertEquals(10000l, userVo.getUserId());
    }

    @Test
    void manageRole(){
        UserForm userForm = new UserForm();
        userForm.setUserRoleId(2l);
        userForm.setLockedMark("NO");
        Boolean flag = (Boolean) userService.manageRole(userForm).get("flag");
        Assertions.assertEquals(true, flag);
        userForm = new UserForm();
        userForm.setUserId(2l);
        flag = (Boolean) userService.manageRole(userForm).get("flag");
        Assertions.assertEquals(false, flag);
        userForm = new UserForm();
        userForm.setLockedMark("NO");
        flag = (Boolean) userService.manageRole(userForm).get("flag");
        Assertions.assertEquals(false, flag);
    }

    @Test
    void userEmpowerment(){
        UserForm userForm = new UserForm();
        userForm.setUserId(10002l);
        userForm.setRoleId(10000);
        Boolean flag = (Boolean) userService.userEmpowerment(userForm).get("flag");
        Assertions.assertEquals(true, flag);
        userForm = new UserForm();
        userForm.setUserId(10002l);
        flag = (Boolean) userService.userEmpowerment(userForm).get("flag");
        Assertions.assertEquals(false, flag);
        userForm = new UserForm();
        userForm.setRoleId(10000);
        flag = (Boolean) userService.userEmpowerment(userForm).get("flag");
        Assertions.assertEquals(false, flag);
        userForm = new UserForm();
        userForm.setUserId(10002l);
        userForm.setRoleId(10001);
        flag = (Boolean) userService.userEmpowerment(userForm).get("flag");
        Assertions.assertEquals(false, flag);
    }

    @Test
    void getEmails(){
        List<Long> userIds = new ArrayList<>();
        userIds.add(10000l);
        userIds.add(10001l);
        List<String> emails = (List<String>) userService.getEmail(userIds).get("data");
        Assertions.assertNotNull(emails);
        userIds = new ArrayList<>();
        emails = (List<String>) userService.getEmail(userIds).get("data");
        Assertions.assertEquals(null, emails);
    }

    @Test
    void getModuleList(){
        Long userId = 10000l;
        Assertions.assertNotNull(userService.getModuleList(userId));
    }



}
