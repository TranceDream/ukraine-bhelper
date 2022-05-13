package com.byb.BaseUtil.Utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.UUID;



/**
 * @author zjt
 * @emile 1214341145@qq.com
 * @date 2022/5/11 22:31
 */

public class UploadPicUtil {
    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 文件上传路径，相对路径
        String filePath = System.getProperty("user.dir")+File.separator+"upload";
        // 获取文件名
        String fileName = file.getOriginalFilename();

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 解决中文问题，liunx下中文路径，图片显示问题
        fileName = UUID.randomUUID() + suffixName;

        File fileDir  = new File(filePath);
        // 检测是否存在目录
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 构建真实的文件路径
        File dest = new File(fileDir.getAbsolutePath() + File.separator + fileName);
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName = "/upload/" + fileName;
        return fileName;
    }
}