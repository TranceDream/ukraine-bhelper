package com.byb.auditservice.Controller;

import com.byb.auditservice.Service.AuditService;
import com.byb.auditservice.Vo.AuditForm;
import com.byb.auditservice.Vo.AuditVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService mockAuditService;

    @Test
    void testAddAudit() throws Exception {
        // Setup
        when(mockAuditService.addAudit(new AuditForm())).thenReturn(new HashMap<>());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/audit/addAudit")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAuditList() throws Exception {
        // Setup
        // Configure AuditService.getAuditList(...).
        final AuditVo auditVo = new AuditVo();
        auditVo.setAuditId(0);
        auditVo.setCreateTime(Date.valueOf(LocalDate.of(2020, 1, 1)));
        auditVo.setObjtypeId(0);
        auditVo.setObjId(0L);
        auditVo.setOperator(0L);
        auditVo.setOper(0);
        auditVo.setOperation("operation");
        auditVo.setMessage("message");
        auditVo.setDeleteMark("deleteMark");
        final List<AuditVo> auditVos = Arrays.asList(auditVo);
        when(mockAuditService.getAuditList(new AuditForm())).thenReturn(auditVos);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/audit/getAuditList")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAuditList_AuditServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAuditService.getAuditList(new AuditForm())).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/audit/getAuditList")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
