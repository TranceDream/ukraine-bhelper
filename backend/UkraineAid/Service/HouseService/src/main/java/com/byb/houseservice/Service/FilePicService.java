package com.byb.houseservice.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byb.houseservice.Entity.FileName;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 23:24
 */
@Repository
public interface FilePicService extends IService<FileName> {
    Map<String,Object> uploadHousePic(FileName fileName);
    Map<String,Object> reHousePic(FileName fileName);
}
