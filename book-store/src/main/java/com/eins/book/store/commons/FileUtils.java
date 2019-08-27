package com.eins.book.store.commons;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 根据id生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String id, String fileOriginName){
        return id + FileUtils.getSuffix(fileOriginName);
    }

    /**
     * @param file 文件
     * @param path 文件存放路径
     * @return
     */
    public static boolean upload(String  id, MultipartFile file, String path) {

        String realPath = path + "/" + FileUtils.getFileName(id, file.getOriginalFilename());

        File dest = new File(realPath);

        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}