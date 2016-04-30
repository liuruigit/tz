package com.jhl.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class QRCodeUtil {

    public static void main(String []args)throws Exception{
//        String text = "http://www.baidu.com";
//        int width = 100;
//        int height = 100;
//        String format = "png";
//        Hashtable hints= new Hashtable();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
//        File outputFile = new File("C:\\Users\\Administrator\\Desktop\\new.png");
//        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

    }

    public static BufferedImage buildQRCode(String content, int width,int height) throws Exception{
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
//        File outputFile = new File("C:\\Users\\Administrator\\Desktop\\new.png");
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
