package com.byb.houseservice.Service;

import com.byb.houseservice.Vo.TagVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 13:41
 */
@Repository
public interface PostTagService {
    Map<String,Object> addPostTag(List<TagVo> list);
    Map<String,Object> deleteTag(int tagid);
    Map<String,Object> selectTag(Map<String,Object> selectCondition);

}
