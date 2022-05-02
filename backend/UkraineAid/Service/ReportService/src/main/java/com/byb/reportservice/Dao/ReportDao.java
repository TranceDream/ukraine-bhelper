package com.byb.reportservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.reportservice.Entity.Report;
import com.byb.reportservice.Vo.ReportForm;
import com.byb.reportservice.Vo.ReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDao extends BaseMapper<Report> {

    @Select(value = "select count(1) from REPORT where OBJTYPE_ID = #{objtypeId} and DELETE_MARK = #{deleteMark}")
    Integer countReportList(Map<String, Object> params);
    List<ReportVo> getReportList(Map<String, Object> params);

    @Select(value = "select count(1) from REPORT where OBJTYPE_ID = #{objtypeId} and DEFENSE = #{defense} and DELETE_MARK = #{deleteMark}")
    Integer countReportDetail(Map<String, Object> params);
    List<ReportVo> getReportDetail(Map<String, Object> params);

    Integer updateIsHandle(List<Report> list);

}
