package core;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FourierService implements FourierActions {

    public double[][] calculateSpectrum(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Создание массива для хранения Фурье-спектра
        double[][] spectrum = new double[height][width];

        // Преобразование каждого пикселя изображения
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double sumReal = 0.0;
                double sumImaginary = 0.0;

                // Вычисление суммы реальной и мнимой частей для каждой частоты
                for (int u = 0; u < width; u++) {
                    for (int v = 0; v < height; v++) {
                        Color pixelColor = new Color(image.getRGB(u, v));
                        double intensity = pixelColor.getRed() / 255.0; // Интенсивность пикселя (от 0.0 до 1.0)
                        double angle = 2 * Math.PI * ((u * x / (double) width) + (v * y / (double) height));
                        sumReal += intensity * Math.cos(angle);
                        sumImaginary -= intensity * Math.sin(angle);
                    }
                }

                // Вычисление амплитуды Фурье-спектра
                double amplitude = Math.sqrt(sumReal * sumReal + sumImaginary * sumImaginary);
                spectrum[y][x] = amplitude;
            }
        }

        return spectrum;
    }

    public Complex[] dft(Complex[] x) {
        int N = x.length;
        Complex[] X = new Complex[N];
        for (int k = 0; k < N; k++) {
            X[k] = new Complex(0, 0);
            for (int n = 0; n < N; n++) {
                double theta = -2 * Math.PI * k * n / N;
                Complex Wn = new Complex(Math.cos(theta), Math.sin(theta));
                X[k] = X[k].add(x[n].mul(Wn));
            }
        }
        return X;
    }

    public Complex[] inverseDft(Complex[] X) {
        int N = X.length;
        Complex[] x = new Complex[N];
        for (int n = 0; n < N; n++) {
            x[n] = new Complex(0, 0);
            for (int k = 0; k < N; k++) {
                double theta = 2 * Math.PI * k * n / N;
                Complex Wn = new Complex(Math.cos(theta), Math.sin(theta));
                x[n] = x[n].add(X[k].mul(Wn));
            }
            x[n] = new Complex(x[n].getReal() / N, x[n].getImag() / N);
        }
        return x;
    }


    public Complex[][] fourierTransform(int[][] input) {
        int M = input.length;
        int N = input[0].length;
        Complex[][] output = new Complex[M][N];
        for (int i = 0; i < M; i++) {
            Complex[] row = new Complex[N];
            for (int j = 0; j < N; j++) {
                row[j] = new Complex(input[i][j], 0);
            }
            row = dft(row);
            for (int j = 0; j < N; j++) {
                output[i][j] = row[j];
            }
        }
        for (int j = 0; j < N; j++) {
            Complex[] col = new Complex[M];
            for (int i = 0; i < M; i++) {
                col[i] = output[i][j];
            }
            col = dft(col);
            for (int i = 0; i < M; i++) {
                output[i][j] = col[i];
            }
        }
        return output;
    }

    public int[][] fourierInverseTransform(Complex[][] signals) {
        int M = signals.length;
        int N = signals[0].length;
        Complex[][] output = new Complex[M][N];
        for (int i = 0; i < M; i++) {
            Complex[] row = new Complex[N];
            for (int j = 0; j < N; j++) {
                row[j] = signals[i][j];
            }
            row = inverseDft(row);
            for (int j = 0; j < N; j++) {
                output[i][j] = row[j];
            }
        }
        for (int j = 0; j < N; j++) {
            Complex[] col = new Complex[M];
            for (int i = 0; i < M; i++) {
                col[i] = output[i][j];
            }
            col = inverseDft(col);
            for (int i = 0; i < M; i++) {
                output[i][j] = col[i];
            }
        }
        int[][] pixels = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                pixels[i][j] = (int) output[i][j].getReal();
            }
        }
        return pixels;
    }
}

