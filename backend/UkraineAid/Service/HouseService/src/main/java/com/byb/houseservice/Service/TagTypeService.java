package com.byb.houseservice.Service;

import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.TagTypeVo;
import com.byb.houseservice.Vo.TagVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 13:42
 */
@Repository
public interface TagTypeService {
    Map<String,Object> addTagType (List<TagTypeVo> list);
    Map<String,Object> deleteTagType(int tagid);
}
