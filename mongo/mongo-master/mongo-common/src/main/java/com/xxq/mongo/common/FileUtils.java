package com.xxq.mongo.common;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ Author     ：XuXiuquan.
 * @ Date       ：Created in 21:59 2020/4/17
 * @ Description：
 * @ Modified By：
 * @Version : 1.0
 */
public class FileUtils {
    public static void downloadFile(HttpServletResponse res, File file, String name) {
        try {
            res.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("utf-8")));
            res.setContentType("aapplication/octet-stream");
            BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());
            InputStream is = new FileInputStream(file.getAbsolutePath());
            BufferedInputStream bis = new BufferedInputStream(is);
            int length = 0;
            byte[] temp = new byte[1*1024*10];
            while ((length = bis.read(temp)) != -1){
                bos.write(temp,0,length);
            }
            bos.flush();
            is.close();
            bos.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        // 判断是否是一个目录, 不是的话跳过, 直接删除; 如果是一个目录, 先将其内容清空.
        if(file.isDirectory()) {
            // 获取子文件/目录
            File[] subFiles = file.listFiles();
            // 遍历该目录
            for (File subFile : subFiles) {
                // 递归调用删除该文件: 如果这是一个空目录或文件, 一次递归就可删除.
                // 如果这是一个非空目录, 多次递归清空其内容后再删除
                deleteFile(subFile);
            }
        }
        // 删除空目录或文件
        file.delete();
    }
}
