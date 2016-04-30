package com.jhl.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Administrator on 2016/2/20.
 */
public class ImageUtil {

    public void test(File fromPic, String toPic)throws Exception{
        BufferedImage image = ImageIO.read(fromPic);
        Thumbnails.Builder<BufferedImage> builder = null;

        int imageWidth = image.getWidth();
        int imageHeitht = image.getHeight();
        if ((float)300 / 400 != (float)imageWidth / imageHeitht) {
            if (imageWidth > imageHeitht) {
                image = Thumbnails.of(fromPic).height(300).asBufferedImage();
            } else {
                image = Thumbnails.of(fromPic).width(400).asBufferedImage();
            }
            builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, 400, 300).size(400, 300);
        } else {
            builder = Thumbnails.of(image).size(400, 300);
        }
        builder.outputFormat("jpg").toFile(toPic);
    }

}
