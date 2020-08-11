package com.sy.basis.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片 工具类
 * @author wangxiao
 * @since 1.1
 */
public class ImageUtil {

    /**
     * 在目标图片上绘画原图片
     * @param desPath
     * @param srcPath
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws IOException
     */
    public static void overlapImage(String desPath,String srcPath,int x,int y, int width,int height) throws IOException {
        BufferedImage a = ImageIO.read(new File(srcPath));
        BufferedImage b = ImageIO.read(new File(desPath));
        Graphics2D gd = a.createGraphics();
        gd.drawImage(b, x, y, width, height, null);
        gd.dispose();
        File file = new File(srcPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        ImageIO.write(a, "jpg", file);
    }
}
