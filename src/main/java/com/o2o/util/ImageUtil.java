package com.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @Author yukai
 * @Date 2018年7月14日
 */
public class ImageUtil {
    // private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将传入的图片进行重命名和缩略，保存到指定文件夹下面(缩略图)
     *
     * @param thumbnail（图片文件流）
     * @param targetAddr
     *            （传入图片要存放的相对地址）
     * @return
     */
    public static String generateThumbnail(ImageHolder thuminal, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thuminal.getImageName());
        makeDirPath(targetAddr);
        // 组成新的相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relative addr:{}", relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current dest addr:{}", dest);
        try {
            // 对图片进行缩略，并保存到指定的绝对文件夹路径
            Thumbnails.of(thuminal.getImage()).size(200, 200).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;

    }

    /**
     * 详情图
     *
     * @param thuminal
     * @param targetAddr
     * @return
     */
    public static String productImgThumbnail(ImageHolder thuminal, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thuminal.getImageName());
        makeDirPath(targetAddr);
        // 组成新的相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relative addr:{}", relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current dest addr:{}", dest);
        try {
            // 对图片进行缩略，并保存到指定的绝对文件夹路径
            Thumbnails.of(thuminal.getImage()).size(337, 640).outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;

    }

    /**
     * 创建文件夹
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        // 获取整个文件路径
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        // 判断路径是否存在，不存在就创建
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

    }

    /**
     * 获取文件的扩展名
     *
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(String imgName) {
        return imgName.substring(imgName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，yyyyMMddHHmmss+5位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        return format.format(new Date()) + (r.nextInt(99999) + 10000);
    }

    /**
     * 判断传入的是文件的路径还是目录的路径 如果是文件路径则删除该文件 如果是目录则删除该目录下的所有文件
     *
     * @param fileOrPath
     */
    public static void deleteFileOrPath(String fileOrPath) {
        File realPath = new File(PathUtil.getImgBasePath() + fileOrPath);
        if (realPath.exists()) {
            if (realPath.isDirectory()) {
                File[] files = realPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            realPath.delete();
        }
    }
}
