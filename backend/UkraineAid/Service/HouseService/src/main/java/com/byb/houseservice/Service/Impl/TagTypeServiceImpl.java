package com.byb.houseservice.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byb.houseservice.Dao.TagTypeMapper;
import com.byb.houseservice.Entity.Tag;
import com.byb.houseservice.Entity.TagType;
import com.byb.houseservice.Service.TagTypeService;
import com.byb.houseservice.Vo.TagTypeVo;
import com.byb.houseservice.Vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/4 14:16
 */
@Service
public class TagTypeServiceImpl extends ServiceImpl<TagTypeMapper, TagType>
        implements TagTypeService {

    @Override
    public Map<String, Object> addTagType(List<TagTypeVo> list) {
        Map<String, Object> result = new HashMap<>();
        try{
            TagType tagtype = new TagType();
            for (TagTypeVo tagTypeVo: list){
                BeanUtils.copyProperties(tagTypeVo,tagtype);
                baseMapper.insert(tagtype);
            }
            result.put("msg","提交成功");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","提交失败");
        }

        return result;
    }

    @Override
    public Map<String, Object> deleteTagType(int tagTypeid) {
        Map<String, Object> result = new HashMap<>();
        try{
            TagType tagType = new TagType();
            tagType.setTypeId(tagTypeid);
            tagType.setDeleteMask("YES");

//            System.out.println(houseinfo);
            baseMapper.updateById(tagType);

            result.put("data",tagTypeid);
            result.put("msg","删除成功");
//            System.out.println("duiduidui");

        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","删除失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> selectTagType(Map<String,Object> selectCondition) {
        Map<String, Object> result = new HashMap<>();
        try{
            List<TagType> tagTypeList = baseMapper.selectByMap(selectCondition);
            result.put("data",tagTypeList);
        }catch(Exception e){
            e.printStackTrace();
            result.put("msg","失败");
        }
        return result;
    }

}
