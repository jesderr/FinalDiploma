package core;

import java.awt.image.BufferedImage;

public interface ImageActions {
    public int[][] convertToPixels(BufferedImage image);

    public BufferedImage saveGrayImage(int[][] pixels);

    public void convertToImage(int[][] pixels);
    int[][] pixelsToGray(BufferedImage image);


}
