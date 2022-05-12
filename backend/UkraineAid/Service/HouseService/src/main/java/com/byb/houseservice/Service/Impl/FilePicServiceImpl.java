package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.FileNameMapper;
import com.byb.houseservice.Entity.FileName;
import com.byb.houseservice.Service.FilePicService;

import java.net.FileNameMap;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 23:27
 */
public class FilePicServiceImpl extends ServiceImpl<FileNameMapper, FileName>
        implements FilePicService {

    @Override
    public Map<String, Object> uploadHousePic(String fileName) {
        return null;
    }
}
