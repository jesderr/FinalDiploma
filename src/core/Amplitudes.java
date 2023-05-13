package core;

public class Amplitudes {
    public double[][] getAmplitudes(Complex[][] complexArray) {
        int rows = complexArray.length;
        int cols = complexArray[0].length;
        double[][] amplitudeArray = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double real = complexArray[i][j].getReal();
                double imag = complexArray[i][j].getImag();
                amplitudeArray[i][j] = Math.sqrt(real * real + imag * imag);
            }
        }
        return amplitudeArray;
    }

    public double[][] normalizeAmplitudes(double[][] amplitudeArray) {
        int rows = amplitudeArray.length;
        int cols = amplitudeArray[0].length;
        double[][] normalizedArray = new double[rows][cols];

        // Находим максимальное значение амплитуды
        double maxAmplitude = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (amplitudeArray[i][j] > maxAmplitude) {
                    maxAmplitude = amplitudeArray[i][j];
                }
            }
        }

        // Нормализуем значения амплитуд
        if (maxAmplitude > 0) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    normalizedArray[i][j] = amplitudeArray[i][j] / maxAmplitude;
                }
            }
        } else {
            // Если все значения амплитуд равны нулю, оставляем массив без изменений
            normalizedArray = amplitudeArray;
        }

        return normalizedArray;
    }
}
