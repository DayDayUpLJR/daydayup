package com.yc.biz;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Component
@Slf4j
public class FastDFSBizImpl implements FastDFSBiz{
    @Autowired
    private FastFileStorageClient storageClient;//fastDfs的客户端

    @Override
    public String uploadFile(MultipartFile file) {
        String path = "group1/M00/00/00/rBIABWVOCvqARXgGAAOA1dwT4AY539.jpg";//未知图片
        try {
            InputStream iis = file.getInputStream();
            //从流中取出的数据，以byte[]形式返回
            log.info("上传文件的名字为："+file.getOriginalFilename());
            log.info("获得扩展名："+ FilenameUtils.getExtension(file.getOriginalFilename()));
            StorePath storePath = storageClient.uploadFile(IOUtils.toByteArray(iis), FilenameUtils.getExtension(file.getOriginalFilename()));
            log.info("上传图片成功，路径信息：" + storePath);
            log.info("fullPath:" + storePath.getFullPath());
            log.info("group:" + storePath.getGroup());
            log.info("path:" + storePath.getPath());

            path = storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return path;
        }
        return path;
    }

}
