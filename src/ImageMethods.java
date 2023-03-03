import java.awt.image.BufferedImage;

public interface ImageMethods {
    public int[][] convertToPixels(BufferedImage image);

    public void convertToImage(int[][] pixels , int width , int height);

}
