package core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {
    private final ImageActions transferImage;
    private final FourierActions fourierService;
    private final FilterService filterService;

    public ImageProcessor() {
        this.transferImage = new TransferImage();
        this.fourierService = new FourierService();
        this.filterService = new FilterService();
    }

    public double getMaxCutoff(BufferedImage image){
        return this.filterService.getMaxDistanse(image);
    }

    public double[][] calculateSpectrum(BufferedImage image){
        return this.fourierService.calculateSpectrum(image);
    }

    public BufferedImage process(BufferedImage bufferedImage, Filter filter, double cutoff, int order) {
        int[][] grayPixels = this.transferImage.pixelsToGray(bufferedImage);
        Complex[][] fourierImage = this.fourierService.fourierTransform(grayPixels);
        Complex[][] fourierImageFFT = switch (filter) {
            case BUTTERWORTH_FILTER -> filterService.butterworthFilter(fourierImage, cutoff, order);
            case HIGH_PASS_FILTER -> filterService.applyHighPassFilter(fourierImage, cutoff);
            case LOW_PASS_FILTER -> filterService.applyLowPassFilter(fourierImage, cutoff);
        };

        int[][] result = fourierService.fourierInverseTransform(fourierImageFFT);
        BufferedImage resultImage = transferImage.saveGrayImage(result);
        return resultImage;
    }

    public void saveImage(BufferedImage saveImage){
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".jpg")) {
                    selectedFile = new File(filePath + ".jpg");
                }

                ImageIO.write(saveImage, "jpg", selectedFile);

                JOptionPane.showMessageDialog(null, "Изображение сохранено успешно.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}
