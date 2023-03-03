import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class TransferImage implements ImageMethods {
    public int[][] convertToPixels(BufferedImage image) {
        if (image == null) {
            System.out.println("Doesnt contain an image.");
            return null;
        }

        final byte[] bytesOfPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        final int width = image.getWidth();
        final int height = image.getHeight();
        int[][] arrayOfPixels = new int[height][width];

        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < bytesOfPixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) bytesOfPixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) bytesOfPixels[pixel + 1] & 0xff); // blue
                argb += (((int) bytesOfPixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) bytesOfPixels[pixel + 3] & 0xff) << 16); // red

//                argb += (((int) bytesOfPixels[pixel] >> 24) & 0xff);
//                argb += (((int) bytesOfPixels[pixel + 1] >> 16) & 0xff);
//                argb += (((int) bytesOfPixels[pixel + 2] >> 8) & 0xff);
//                argb += ((int) bytesOfPixels[pixel + 3] & 0xff);

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
                argb += -16777216; // 255 alpha
                argb += ((int) bytesOfPixels[pixel] & 0xff); // blue
                argb += (((int) bytesOfPixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) bytesOfPixels[pixel + 2] & 0xff) << 16); // red
//                argb += (((int) bytesOfPixels[pixel + 1] >> 16) & 0xff);
//                argb += (((int) bytesOfPixels[pixel + 2] >> 8) & 0xff);
//                argb += ((int) bytesOfPixels[pixel + 3] & 0xff);
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

    public void convertToImage(int[][] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                image.setRGB(i, j, pixels[i][j]);
            }
        }

        try {
            ImageIO.write(image, "png", new File("D:/test/test1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}