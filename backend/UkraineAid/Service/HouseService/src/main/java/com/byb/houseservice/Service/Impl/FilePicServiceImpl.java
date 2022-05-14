package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.FileNameMapper;
import com.byb.houseservice.Entity.FileName;
import com.byb.houseservice.Service.FilePicService;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
             result.put("code",400);
            return result;
        }
        result.put("msg","success");
        result.put("code",200);
        return result;
    }

    @Override
    public Map<String, Object> reHousePic(FileName fileName) {
        Map<String,Object> select = new HashMap<>();
        select.put("houseId",fileName.getHouseId());
        Map<String,Object> result = new HashMap<>();
        List<String> fileList = new ArrayList<>();
        List<FileName> fileNames = baseMapper.selectByMap(select);
        for (FileName fileName1 : fileNames){
            fileList.add(fileName1.getFilePath());
        }
        result.put("fileNames",fileList);
        return result;
    }


}
