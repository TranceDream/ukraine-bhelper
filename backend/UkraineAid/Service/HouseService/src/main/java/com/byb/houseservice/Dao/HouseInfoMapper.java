package com.byb.houseservice.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byb.houseservice.Entity.HouseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author zjt
 * @date 2022/5/4 1:20
 */
@Mapper
public interface HouseInfoMapper extends BaseMapper<HouseInfo> {

}
