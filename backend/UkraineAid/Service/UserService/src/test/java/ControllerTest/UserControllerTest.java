package ControllerTest;

import com.alibaba.fastjson.JSONObject;
import com.byb.BaseUtil.Utils.Result;
import com.byb.userservice.Controller.UserController;
import com.byb.userservice.Entity.User;
import com.byb.userservice.UserServiceApplication;
import com.byb.userservice.Vo.UserForm;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@SpringBootTest(classes = {UserServiceApplication.class})
public class UserControllerTest {

//    @Autowired
//    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"userId\":10000}","{}","{\"roleId\":10000,\"orderText\":\"ur.CREATE_TIME asc\"}","{\"name\":\"张三\"}","{\"pageSize\":2}"})
    void getUserList(String arg) throws Exception {
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getUserList")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            System.out.println(mvcResult);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"userId\":10000}"})
    void getUserDetail1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getUserDetail")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(200, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}","{\"roleId\":10000,\"orderText\":\"ur.CREATE_TIME asc\"}","{\"name\":\"张三\"}"})
    void getUserDetail2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getUserDetail")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"userRoleId\":10000,\"lockedMark\":\"NO\"}"})
    void manageRole1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/manageRole")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"userRoleId\":10000}","{\"lockedMark\":\"NO\"}","{\"userId\":10000,\"lockedMark\":\"DD\"}"})
    void manageRole2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/manageRole")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleId\":\"10000,\"userId\":10002}"})
    void userEmpowerment1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/userEmpowerment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleId\":\"10000","{\"userId\":10002}"})
    void userEmpowerment2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/userEmpowerment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"pageSize\":5}","{}","{\"roleId\":10004}","{\"pageNo\":1,\"roleName\":\"管理\"}"})
    void getRoleList(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getRoleList")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleId\":10000},{\"roleId\":10000,\"roleName\":\"管理员\"}"})
    void getRoleDetail1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getRoleDetail")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleName\":\"管理员\"}"})
    void getRoleDetail2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getRoleDetail")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleName\":\"测试\",\"permissions\":[10000]}"})
    void addRole1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/addRole")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"roleId\":10000,\"permissions\":[10000]}"})
    void addRole2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/addRole")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}","{\"pageSize\":10, \"pageNo\":1}","{\"permissionId\":10003}"})
    void getPermissionList(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getPermissionList")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"permissionName\":\"测试\", \"url\":\"\\user\\test\"}"})
    void addPermission1(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/addPermission")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"permissionName\":\"测试\"}","{\"url\":\"\\user\\test\"}"})
    void addPermission2(String arg){
        MvcResult mvcResult = null;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/addPermission")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(arg))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            Result<Map<String, Object>> result = JSONObject.parseObject(mvcResult.toString(), Result.class);
            int code = result.getCode();
            Assertions.assertEquals(500, code);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
