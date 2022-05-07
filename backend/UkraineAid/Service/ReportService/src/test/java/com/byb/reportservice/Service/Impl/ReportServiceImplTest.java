package com.byb.reportservice.Service.Impl;

import com.byb.reportservice.Dao.ReportDao;
import com.byb.reportservice.Entity.Report;
import com.byb.reportservice.Vo.ReportForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportDao mockReportDao;

    @InjectMocks
    private ReportServiceImpl reportServiceImplUnderTest;

    @Test
    void testAddReport() {
        // Setup
        final ReportForm reportForm = new ReportForm();
        reportForm.setReportId(0);
        reportForm.setCreateTime(Date.valueOf(LocalDate.of(2020, 1, 1)));
        reportForm.setObjtypeId(0);
        reportForm.setDefense(0L);
        reportForm.setReason("reason");
        reportForm.setProsecution(0L);
        reportForm.setHandleMark("handleMark");
        reportForm.setDeleteMark("deleteMark");
        reportForm.setPageSize(0);
        reportForm.setPageNo(0);

        // Run the test
        final Map<String, Object> result = reportServiceImplUnderTest.addReport(reportForm);

        // Verify the results
    }

    @Test
    void testGetReportList() {
        // Setup
        final ReportForm reportForm = new ReportForm();
        reportForm.setReportId(0);
        reportForm.setCreateTime(Date.valueOf(LocalDate.of(2020, 1, 1)));
        reportForm.setObjtypeId(0);
        reportForm.setDefense(0L);
        reportForm.setReason("reason");
        reportForm.setProsecution(0L);
        reportForm.setHandleMark("handleMark");
        reportForm.setDeleteMark("deleteMark");
        reportForm.setPageSize(0);
        reportForm.setPageNo(0);

        // Run the test
        final Map<String, Object> result = reportServiceImplUnderTest.getReportList(reportForm);

        // Verify the results
    }

    @Test
    void testGetReportDetail() {
        // Setup
        final ReportForm reportForm = new ReportForm();
        reportForm.setReportId(0);
        reportForm.setCreateTime(Date.valueOf(LocalDate.of(2020, 1, 1)));
        reportForm.setObjtypeId(0);
        reportForm.setDefense(0L);
        reportForm.setReason("reason");
        reportForm.setProsecution(0L);
        reportForm.setHandleMark("handleMark");
        reportForm.setDeleteMark("deleteMark");
        reportForm.setPageSize(0);
        reportForm.setPageNo(0);

        // Run the test
        final Map<String, Object> result = reportServiceImplUnderTest.getReportDetail(reportForm);

        // Verify the results
    }

    @Test
    void testDoAudit() {
        // Setup
        final ReportForm reportForm = new ReportForm();
        reportForm.setReportId(0);
        reportForm.setCreateTime(Date.valueOf(LocalDate.of(2020, 1, 1)));
        reportForm.setObjtypeId(0);
        reportForm.setDefense(0L);
        reportForm.setReason("reason");
        reportForm.setProsecution(0L);
        reportForm.setHandleMark("handleMark");
        reportForm.setDeleteMark("deleteMark");
        reportForm.setPageSize(0);
        reportForm.setPageNo(0);

        when(mockReportDao.updateIsHandle(Arrays.asList(new Report()))).thenReturn(0);

        // Run the test
        final Boolean result = reportServiceImplUnderTest.doAudit(reportForm);

        // Verify the results
        assertThat(result).isTrue();
    }
}
