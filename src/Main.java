import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        TransferImage transferImage = new TransferImage();
        FourierClass fourier = new FourierClass();
        Amplitudes ampl = new Amplitudes();
        Filters filter = new Filters();

        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(new File("D:/test/test.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert bufferedImage != null;

        //int[][] pixels = transferImage.convertToPixels(bufferedImage);

//        Complex[][] arrayOfComplex = fourier.fourierTransform(pixels);
//
//        int[][] result = fourier.fourierInverseTransform(arrayOfComplex);
//
//        transferImage.convertToImage(result);
//


        // Преобразование изображения в двумерный массив серых пикселей
        int[][] grayPixels = transferImage.pixelsToGray(bufferedImage);

        //int[][] resultHFreq = filter.enhanceHighFrequency(grayPixels,30,2);
        //transferImage.saveGrayImage(resultHFreq);

        //transferImage.saveGrayImage(grayPixels);
        // Получение фурье-образа изображения
        Complex[][] fourierImage = fourier.fourierTransform(grayPixels);

        // Применение фильтра на фурье-образ

        double cutoff = 145.0;
        Complex[][] fourierImageFFT = filter.applyLowPassFilter(fourierImage,cutoff);
//        Complex[][] fourierImageFFT = filter.applyHighPassFilter(fourierImage,cutoff);
//        fourierImage = filter.applyLowPassFilter(fourierImage,cutoff);
//        cutoff = 20.0;
//        fourierImage = filter.applyHighPassFilter(fourierImage,cutoff);
       // int order = 0;
        //Complex[][] fourierImageFFT = filter.butterworthFilter(fourierImage,cutoff,order);

        int[][] result = fourier.fourierInverseTransform(fourierImageFFT);

        transferImage.saveGrayImage(result);


//        // Получение амплитуд из фурье-образа
//        double[][] amplitudes = ampl.getAmplitudes(fourierImage);

//        // Нормализация значений амплитуд
//        ampl.normalizeAmplitudes(amplitudes);
//
//        // Создание нового изображения из массива амплитуд
//        BufferedImage filteredImage = transferImage.createImageFromAmplitudes(amplitudes);

        // Вывод изображения на экран
//        JFrame frame = new JFrame();
//        frame.getContentPane().setLayout(new FlowLayout());
//        frame.getContentPane().add(new JLabel(new ImageIcon(filteredImage)));
//        frame.pack();
//        frame.setVisible(true);
    }
}

//        АМЛИТУДЫ(ФУРЬЕ ОБРАЗ)
//        double[][] normAmpl = f.normalizeAmplitudes(f.getAmplitudes(arrayOfComplex));
//
//        BufferedImage amplImag = transferImage.createImageFromAmplitudes(normAmpl);
//
//        try {
//            ImageIO.write(amplImag, "png", new File("D:/test/testAmpl.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        for (int i = 0; i < pixels.length; i++) {
//            for (int j = 0; j < pixels[i].length; j++) {
//                System.out.print(" Pixel[" + i + "]" + "[" + j + "]= " + pixels[i][j]);
//            }
//            System.out.println(" \n");
//        }


//        int[][] grayPixels = transferImage.pixelsToGray(bufferedImage);
//        transferImage.saveGrayImage(pixels);// 0 do 255

//        for (int i = 0; i < arrayOfComplex.length; i++) {
//            for (int j = 0; j < arrayOfComplex[i].length; j++) {
//                System.out.print(" Fourier[" + i + "]" + "[" + j + "]= " +
//                        Math.sqrt(arrayOfComplex[i][j].real * arrayOfComplex[i][j].real +
//                                arrayOfComplex[i][j].imag * arrayOfComplex[i][j].imag)
//                                / (arrayOfComplex.length * arrayOfComplex[i].length));
//            }
//            System.out.println(" \n");
//        }
