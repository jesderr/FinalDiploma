package core.interfaces;

import java.awt.image.BufferedImage;

public interface ImageActions {
     int[][] convertToPixels(BufferedImage image);

     BufferedImage getGrayImage(int[][] pixels);

     void convertToImage(int[][] pixels);
    int[][] pixelsToGray(BufferedImage image);

    int[][] pixelsToGrayP(int[][] pixels) ;

     int[][] grayToRGB(int[][] grayPixels);


}
