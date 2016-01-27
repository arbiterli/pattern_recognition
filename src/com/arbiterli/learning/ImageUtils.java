package com.arbiterli.learning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import com.arbiterli.model.Matrix;

@SuppressWarnings("unchecked")
public class ImageUtils {
    public static Set<String> suffixes = new HashSet(Arrays.asList("jpg"));;

    public void grayAndResize(String src, String dest) throws Exception {
        BufferedImage srcImage = ImageIO.read(new File(src));
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        srcImage = op.filter(srcImage, null);
        Image image = srcImage.getScaledInstance(320, 320, Image.SCALE_DEFAULT);
        BufferedImage resizedImage = new BufferedImage(320, 320, BufferedImage.TYPE_INT_RGB);
        Graphics g = resizedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIO.write(resizedImage, "JPEG", new File(dest));
    }

    public Matrix loadGreyImage(String src) throws Exception {
        BufferedImage si = ImageIO.read(new File(src));
        int type = si.getType();
        if (type != BufferedImage.TYPE_BYTE_GRAY) {
            System.out.println("not grey image!");
            return null;
        }
        Matrix matrix = new Matrix(si.getWidth(), si.getHeight());
        for (int i = 0; i < si.getWidth(); ++i) {
            for (int j = 0; j < si.getHeight(); ++j) {
                Color color = new Color(si.getRGB(i, j));
                matrix.setMatrixValue(color.getBlue(), i, j);
            }
        }
        return matrix;
    }

    public void collectImages(File dir, String des) throws Exception {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                collectImages(file, des);
            }
            int suffixPos = file.getName().lastIndexOf(".");
            if (suffixPos == -1) {
                continue;
            }
            String suffix = file.getName().substring(suffixPos + 1).toLowerCase();
            if (suffixes.contains(suffix) && file.length() > 0 && isSizeEnough(file)) {
                copyFile(file, des);
            }
        }
    }

    private boolean isSizeEnough(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return image != null && image.getHeight() > 200 && image.getWidth() > 200
                && Math.abs(image.getHeight() - image.getWidth()) < 100;
    }

    private boolean copyFile(File srcFile, String desPath) throws IOException {
        if (!srcFile.exists()) {
            return false;
        }
        String filePath = desPath;
        File desFolder = new File(filePath);
        if (!desFolder.exists()) {
            if (!filePath.endsWith(File.separator)) {
                filePath += File.separator;
            }
            desFolder = new File(filePath);
            desFolder.mkdirs();
        }
        FileChannel fcin = new FileInputStream(srcFile).getChannel();
        FileChannel fcout = new FileOutputStream(new File(desFolder, srcFile.getName())).getChannel();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        return true;
    }
}
