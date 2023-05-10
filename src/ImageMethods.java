
import java.awt.image.BufferedImage;

public interface ImageMethods {
    public int[][] convertToPixels(BufferedImage image);

    public void saveGrayImage(int[][] pixels);

    public void convertToImage(int[][] pixels);


}
