import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TransferImage transferImage = new TransferImage();

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(new File("D:/test/test.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] pixels = transferImage.convertToPixels(bufferedImage);

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                System.out.print("Pixel[" + i + "]" + "[" + j + "]=" + pixels[i][j] + " ");
            }
            System.out.println("\n");
        }

//        FourierClass fourierClass = new FourierClass();
//
//        int[][] signals = fourierClass.fourierTransform(pixels);


//        for (int i = 0; i < signals.length; i++) {
//            for (int j = 0; j < signals[i].length; j++) {
//                System.out.print("Pixel[" + i + "]" + "[" + j + "]=" + signals[i][j] + " ");
//            }
//            System.out.println("\n");
//        }

        transferImage.convertToImage(pixels, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
}