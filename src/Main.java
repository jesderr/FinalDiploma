import gui.GuiWrapper;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiWrapper();
            }
        });



//        core.TransferImage transferImage = new core.TransferImage();
//        core.FourierClass fourier = new core.FourierClass();
//        core.Amplitudes ampl = new core.Amplitudes();
//        core.Filters filter = new core.Filters();
//
//        BufferedImage bufferedImage = null;
//
//        try {
//            bufferedImage = ImageIO.read(new File("D:/test/test.jpg"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assert bufferedImage != null;
//
//        //int[][] pixels = transferImage.convertToPixels(bufferedImage);
//        // Преобразование изображения в двумерный массив серых пикселей
//        int[][] grayPixels = transferImage.pixelsToGray(bufferedImage);
//
//        //int[][] resultHFreq = filter.enhanceHighFrequency(grayPixels,30,2);
//        //transferImage.saveGrayImage(resultHFreq);
//
//        //transferImage.saveGrayImage(grayPixels);
//        // Получение фурье-образа изображения
//        core.Complex[][] fourierImage = fourier.fourierTransform(grayPixels);
//
//        // Применение фильтра на фурье-образ
//
//        double cutoff = -100.0;
////        core.Complex[][] fourierImageFFT = filter.applyLowPassFilter(fourierImage,cutoff);
////        core.Complex[][] fourierImageFFT = filter.applyHighPassFilter(fourierImage,cutoff);
////        fourierImage = filter.applyLowPassFilter(fourierImage,cutoff);
////        cutoff = 20.0;
////        fourierImage = filter.applyHighPassFilter(fourierImage,cutoff);
//        int order = 2;
//        core.Complex[][] fourierImageFFT = filter.butterworthFilter(fourierImage,cutoff,order);
//
//        int[][] result = fourier.fourierInverseTransform(fourierImageFFT);
//
//        transferImage.saveGrayImage(result);
//


    }
}


//        // Получение амплитуд из фурье-образа
//        double[][] amplitudes = ampl.getAmplitudes(fourierImage);

//        // Нормализация значений амплитуд
//        ampl.normalizeAmplitudes(amplitudes);
//
//        // Создание нового изображения из массива амплитуд
//        BufferedImage filteredImage = transferImage.createImageFromAmplitudes(amplitudes);

// Вывод изображения на экран

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
