package core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {
    private final TransferImage transferImage;
    private final FourierService fourier;
    private final Amplitudes ampl;
    private final FilterService filterService;
    private Filter filter;
    private double cutoff;
    private int order;

    public ImageProcessor(Filter filter, double cutoff, int order) {
        this.transferImage = new TransferImage();
        this.fourier = new FourierService();
        this.ampl = new Amplitudes();
        this.filterService = new FilterService();
        this.filter = filter;
        this.cutoff = cutoff;
        this.order = order;
    }
    public ImageProcessor(Filter filter, double cutoff) {
        this.transferImage = new TransferImage();
        this.fourier = new FourierService();
        this.ampl = new Amplitudes();
        this.filterService = new FilterService();
        this.filter = filter;
        this.cutoff = cutoff;
    }

    public double getMaxCutoff(BufferedImage image){
        this.cutoff =  this.filterService.getMaxDistanse(image);
        return this.cutoff;
    }

    public Complex[][] getComplex(BufferedImage image){
        return this.fourier.fourierTransform(this.transferImage.convertToPixels(image));
    }


    public BufferedImage process(BufferedImage bufferedImage) {
        int[][] grayPixels = transferImage.pixelsToGray(bufferedImage);
        Complex[][] fourierImage = fourier.fourierTransform(grayPixels);
        Complex[][] fourierImageFFT = switch (filter) {
            case BUTTERWORTH_FILTER -> filterService.butterworthFilter(fourierImage, this.cutoff, this.order);
            case HIGH_PASS_FILTER -> filterService.applyHighPassFilter(fourierImage, this.cutoff);
            case LOW_PASS_FILTER -> filterService.applyLowPassFilter(fourierImage, this.cutoff);
        };

        int[][] result = fourier.fourierInverseTransform(fourierImageFFT);
        BufferedImage resultImage = transferImage.saveGrayImage(result);
        return resultImage;
    }

    public void saveImage(BufferedImage saveImage){
        try {
            // Выбор файла и пути сохранения изображения
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                // Получение выбранного файла
                File selectedFile = fileChooser.getSelectedFile();

                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".jpg")) {
                    selectedFile = new File(filePath + ".jpg");
                }

                // Сохранение изображения в выбранный файл
                ImageIO.write(saveImage, "jpg", selectedFile);

                // Вывод сообщения об успешном сохранении
                JOptionPane.showMessageDialog(null, "Изображение сохранено успешно.");
            }
        } catch (Exception e) {
            // Обработка ошибки при сохранении изображения
            JOptionPane.showMessageDialog(null, "Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}
