package core.impl;

import core.impl.Complex;

import java.awt.image.BufferedImage;

public class FilterService {

    public double getMaxDistanse(BufferedImage image){
        int rows = image.getHeight();
        int cols = image.getWidth();
        int centerX = cols / 2;
        int centerY = rows / 2;
        double maxDistance = Double.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double distance = Math.sqrt(Math.pow(i - centerY, 2) + Math.pow(j - centerX, 2));
                if(distance > maxDistance){
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }
    public Complex[][] butterworthFilter(Complex[][] imageFFT, double cutoff, int order) {
        int rows = imageFFT.length;
        int cols = imageFFT[0].length;
        int centerX = cols / 2;
        int centerY = rows / 2;

        Complex[][] filteredFFT = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double distance = Math.sqrt(Math.pow(i - centerY, 2) + Math.pow(j - centerX, 2));
                double butterworth = 1 / (1 + Math.pow(distance / cutoff, 2 * order));
                filteredFFT[i][j] = imageFFT[i][j].multiply(butterworth);
            }
        }
        return filteredFFT;
    }

    //фильтр высоких частот(удаляет низкочастотные компоненты изображения,оставляя только высокочастотные
    //детали(края,текстуры,шумы)(полезно для выделения контуров или подчеркивания текстур в изображении)
    public Complex[][] applyHighPassFilter(Complex[][] imageFFT, double cutoff) {
        int rows = imageFFT.length;
        int cols = imageFFT[0].length;
        // создаем новый двумерный массив комплексных чисел, чтобы сохранить измененный Фурье-образ
        Complex[][] filteredFFT = new Complex[rows][cols];
        // вычисляем центр изображения, чтобы определить расстояние от каждой точки до центра
        int centerX = cols / 2;
        int centerY = rows / 2;
        // проходимся по каждой точке Фурье-образа
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // вычисляем расстояние от текущей точки до центра
                double distance = Math.sqrt(Math.pow(i - centerY, 2) + Math.pow(j - centerX, 2));
                // если расстояние больше заданного порога, оставляем значение комплексного числа без изменений
                if (distance > cutoff) {
                    filteredFFT[i][j] = imageFFT[i][j];
                } else {
                    // иначе, обнуляем значение комплексного числа
                    filteredFFT[i][j] = new Complex(0, 0);
                }
            }
        }
        return filteredFFT;
    }
    //фильтр нижних частот, который оставляет только низкочастотные компоненты изображения
    public Complex[][] applyLowPassFilter(Complex[][] imageFFT, double cutoff) {
        int rows = imageFFT.length;
        int cols = imageFFT[0].length;
        // создаем новый двумерный массив комплексных чисел, чтобы сохранить измененный Фурье-образ
        Complex[][] filteredFFT = new Complex[rows][cols];
        // вычисляем центр изображения, чтобы определить расстояние от каждой точки до центра
        int centerX = cols / 2;
        int centerY = rows / 2;
        // проходимся по каждой точке Фурье-образа
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // вычисляем расстояние от текущей точки до центра
                //Math.sqrt(Math.pow(i - centerY, 2) + Math.pow(j - centerX, 2))
                double distance = Math.sqrt(Math.pow(i - centerY, 2) + Math.pow(j - centerX, 2));
                // если расстояние меньше заданного порога, оставляем значение комплексного числа без изменений
                if (distance <= cutoff) {
                    filteredFFT[i][j] = imageFFT[i][j];
                } else {
                    // иначе, обнуляем значение комплексного числа
                    filteredFFT[i][j] = new Complex(0, 0);
                }
            }
        }
        return filteredFFT;
    }

//    public int[][] enhanceHighFrequency(int[][] image, double cutoff, int order) {
//        // Применяем дискретное двумерное преобразование Фурье
//        core.impl.Complex[][] imageFFT = f.fourierTransform(image);
//
//        // Применяем фильтр Баттерворта для усиления высоких частот
//        core.impl.Complex[][] filteredFFT = butterworthFilter(imageFFT, cutoff, order);
//
//        // Преобразуем обратно в пространственное представление
//        int[][] filteredImage = f.fourierInverseTransform(filteredFFT);
//
//        // Вычисляем гистограмму изображения
//        int[] histogram = computeHistogram(filteredImage);
//
//        // Вычисляем кумулятивную функцию распределения
//        int[] cumulativeHistogram = computeCumulativeHistogram(histogram);
//
//        // Вычисляем коэффициент эквализации
//        double equalizationFactor = (double) (histogram.length - 1) / (filteredImage.length * filteredImage[0].length);
//
//        // Применяем эквализацию гистограммы
//        int[][] equalizedImage = applyHistogramEqualization(filteredImage, cumulativeHistogram, equalizationFactor);
//
//        return equalizedImage;
//    }
//
//    private int[] computeHistogram(int[][] image) {
//        int[] histogram = new int[256]; // Гистограмма с 256 возможными значениями яркости
//
//        int rows = image.length;
//        int cols = image[0].length;
//
//        // Подсчет количества пикселей для каждого значения яркости
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                int pixelValue = image[i][j];
//                histogram[pixelValue]++;
//            }
//        }
//
//        return histogram;
//    }
//
//    private int[] computeCumulativeHistogram(int[] histogram) {
//        int[] cumulativeHistogram = new int[256]; // Кумулятивная гистограмма с 256 возможными значениями яркости
//
//        cumulativeHistogram[0] = histogram[0]; // Начальное значение кумулятивной гистограммы равно значению гистограммы для первого значения яркости
//
//        // Вычисление кумулятивной гистограммы путем суммирования значений гистограммы
//        for (int i = 1; i < 256; i++) {
//            cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
//        }
//
//        return cumulativeHistogram;
//    }
//
//    private int[][] applyHistogramEqualization(int[][] image, int[] cumulativeHistogram, double equalizationFactor) {
//        int rows = image.length;
//        int cols = image[0].length;
//
//        int[][] equalizedImage = new int[rows][cols];
//
//        int totalPixels = rows * cols;
//        double normalizedFactor = equalizationFactor / totalPixels;
//
//        // Применение эквализации гистограммы к изображению
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                int pixelValue = image[i][j];
//                int equalizedValue = (int) Math.round(cumulativeHistogram[pixelValue] * normalizedFactor);
//                equalizedImage[i][j] = equalizedValue;
//            }
//        }
//
//        return equalizedImage;
//    }
}
