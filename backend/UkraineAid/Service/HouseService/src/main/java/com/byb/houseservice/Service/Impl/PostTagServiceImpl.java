package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.TagMapper;
import com.byb.houseservice.Entity.Contact;
import com.byb.houseservice.Entity.Tag;
import com.byb.houseservice.Service.PostTagService;
import com.byb.houseservice.Vo.ContactVo;
import com.byb.houseservice.Vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 14:08
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements PostTagService {

    @Override
    public Map<String, Object> addPostTag(List<TagVo> list) {
        Map<String, Object> result = new HashMap<>();
        try{
            Tag tag = new Tag();
            for (TagVo tagVo: list){
                BeanUtils.copyProperties(tagVo,tag);
                baseMapper.insert(tag);
            }

            result.put("msg","提交成功");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","提交失败");
        }

        return result;
    }
}
