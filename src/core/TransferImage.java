package core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;


public class TransferImage implements ImageActions {

    public int[][] convertToPixels(BufferedImage image) {

        if (image == null) {
            System.out.println("Doesnt contain an image.");
            return null;
        }

        final byte[] bytesOfPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
        final int width = image.getWidth();
        final int height = image.getHeight();
        int[][] arrayOfPixels = new int[width][height];

        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < bytesOfPixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) bytesOfPixels[pixel] & 0xff) << 24);
                argb += ((int) bytesOfPixels[pixel + 1] & 0xff);
                argb += (((int) bytesOfPixels[pixel + 2] & 0xff) << 8);
                argb += (((int) bytesOfPixels[pixel + 3] & 0xff) << 16);
                arrayOfPixels[col][row] = argb;
                col++;
                if (col == image.getWidth()) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < bytesOfPixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216;
                argb += ((int) bytesOfPixels[pixel] & 0xff);
                argb += (((int) bytesOfPixels[pixel + 1] & 0xff) << 8);
                argb += (((int) bytesOfPixels[pixel + 2] & 0xff) << 16);
                arrayOfPixels[col][row] = argb;
                col++;
                if (col == image.getWidth()) {
                    col = 0;
                    row++;
                }
            }
        }
        return arrayOfPixels;
    }

    public int[][] pixelsToGray(BufferedImage image) {
        if (image == null) {
            System.out.println("Doesnt contain an image.");
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int rgb = image.getRGB(y, x);
                Color color = new Color(rgb);
                int grayscale = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                pixels[x][y] = grayscale;
            }
        }
        return pixels;
    }

    public BufferedImage saveGrayImage(int[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grayscale = pixels[i][j];
                int rgb = ((grayscale << 16) & 0xff0000) |
                        ((grayscale << 8) & 0xff00) | (grayscale & 0xff);
                image.setRGB(j, i, rgb);
            }
        }
//        try {
//            ImageIO.write(image, "jpg", new File("D:/test/testGrayPixels.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return image;
    }

    public void tryToSave(BufferedImage finalImage){
        try {
            ImageIO.write(finalImage, "jpg", new File("D:/test/testGrayPixels.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] grayToRGB(int[][] grayPixels){
        int height = grayPixels.length;
        int width = grayPixels[0].length;
        int[][] rgbPixels = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int gray = grayPixels[i][j];
                int rgb = (gray << 16) | (gray << 6) | gray;
                rgbPixels[i][j] = rgb;
            }
        }
        return rgbPixels;
    }

    public int[][] imageToGrayPixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] grayPixels = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row);
                int gray = (int) (0.2989 * ((rgb >> 16) & 0xFF) + 0.5870 * ((rgb >> 8) & 0xFF) + 0.1140 * (rgb & 0xFF));
                grayPixels[row][col] = gray;
            }
        }
        return grayPixels;
    }

    public BufferedImage createImageFromAmplitudes(double[][] amplitudes) {
        int height = amplitudes.length;
        int width = amplitudes[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        double maxAmplitude = getMaxAmplitude(amplitudes);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = (int) (255 * amplitudes[y][x] / maxAmplitude);
                image.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }
        return image;

    }

    private double getMaxAmplitude(double[][] amplitudes) {
        double max = 0;
        for (double[] row : amplitudes) {
            for (double amplitude : row) {
                if (amplitude > max) {
                    max = amplitude;
                }
            }
        }
        return max;
    }

    public void convertToImage(int[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                image.setRGB(x, y, pixels[x][y]);
            }
        }
        try {
            ImageIO.write(image, "jpg", new File("D:/test/testSaveFourier.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    СТРОИТ ФУРЬЕ-ОБРАЗ(ДОДЕЛАТЬ)
//    public BufferedImage createImageFromAmplitudes(double[][] amplitudes) {
//        int height = amplitudes.length;
//        int width = amplitudes[0].length;
//
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//
//        double maxAmplitude = getMaxAmplitude(amplitudes);
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int gray = (int) (255 * amplitudes[y][x] / maxAmplitude);
//                image.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
//            }
//        }
//        return image;
//
//    }
//
//    private double getMaxAmplitude(double[][] amplitudes) {
//        double max = 0;
//        for (double[] row : amplitudes) {
//            for (double amplitude : row) {
//                if (amplitude > max) {
//                    max = amplitude;
//                }
//            }
//        }
//        return max;
//    }

}




/*

    public int[][] convertToPixelsTYPA(BufferedImage image) {
        final int width = image.getWidth();
        final int height = image.getHeight();
        int[][] arrayOfPixels = new int[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arrayOfPixels[i][j] = image.getRGB(i,j);
            }
        }
        return arrayOfPixels;
    }




public int[][] pixelsToGray(int[][] pixels) {
        int width = pixels.length;
        int height = pixels[0].length;
        int[][] grayPixels = new int[height][width];

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int pixel = pixels[x][y];
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                int grayscale = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
                pixels[x][y] = grayscale;
            }
        }
        return grayPixels;
    }

 */