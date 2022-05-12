package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.FileNameMapper;
import com.byb.houseservice.Entity.FileName;
import com.byb.houseservice.Service.FilePicService;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 23:27
 */

@Service
public class FilePicServiceImpl extends ServiceImpl<FileNameMapper, FileName>
        implements FilePicService {

    @Override
    public Map<String, Object> uploadHousePic(FileName fileName) {
        Map<String, Object> result = new HashMap<>();
        try{
            baseMapper.insert(fileName);
        }catch (Exception e){
            e.printStackTrace();
             result.put("msg","fail");
            return result;
        }
       result.put("msg","success");
        return result;
    }

    @Override
    public Map<String, Object> reHousePic(FileName fileName) {
        return null;
    }
}
