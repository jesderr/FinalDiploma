package core;

public class FourierService implements FourierActions {

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
//    // Calculates the 1D DFT of the input signal
//    public core.Complex[] dft(core.Complex[] signal) {
//        int N = signal.length;
//        core.Complex[] spectrum = new core.Complex[N];
//        for (int k = 0; k < N; k++) {
//            core.Complex sum = new core.Complex(0, 0);
//            for (int n = 0; n < N; n++) {
//                double angle = 2 * Math.PI * k * n / N;
//                core.Complex twiddle = new core.Complex(Math.cos(angle), -Math.sin(angle));
//                sum = sum.add(signal[n].mul(twiddle));
//            }
//            spectrum[k] = sum;
//        }
//        return spectrum;
//    }
//
//    // Calculates the 2D DFT of the input image
//    public core.Complex[][] fourierTransform(int[][] image) {
//        int numRows = image.length;
//        int numCols = image[0].length;
//
//        // Convert the image array to a core.Complex array
//        core.Complex[][] signal = new core.Complex[numRows][numCols];
//        for (int y = 0; y < numRows; y++) {
//            for (int x = 0; x < numCols; x++) {
//                signal[y][x] = new core.Complex(image[y][x], 0);
//            }
//        }
//
//        // Perform the DFT on each row
//        core.Complex[][] rowSpectrum = new core.Complex[numRows][numCols];
//        for (int y = 0; y < numRows; y++) {
//            rowSpectrum[y] = dft(signal[y]);
//        }
//
//        // Transpose the result and perform the DFT on each row
//        core.Complex[][] colSpectrum = new core.Complex[numCols][numRows];
//        for (int x = 0; x < numCols; x++) {
//            core.Complex[] column = new core.Complex[numRows];
//            for (int y = 0; y < numRows; y++) {
//                column[y] = rowSpectrum[y][x];
//            }
//            core.Complex[] columnSpectrum = dft(column);
//            for (int y = 0; y < numRows; y++) {
//                colSpectrum[x][y] = columnSpectrum[y];
//            }
//        }
//
//        return colSpectrum;
//    }
//
//    // Calculates the 1D IDFT of the input spectrum
//    public core.Complex[] idft(core.Complex[] spectrum) {
//        int N = spectrum.length;
//        core.Complex[] signal = new core.Complex[N];
//        for (int n = 0; n < N; n++) {
//            core.Complex sum = new core.Complex(0, 0);
//            for (int k = 0; k < N; k++) {
//                double angle = 2 * Math.PI * k * n / N;
//                core.Complex twiddle = new core.Complex(Math.cos(angle), Math.sin(angle));
//                sum = sum.add(spectrum[k].mul(twiddle));
//            }
//            signal[n] = new core.Complex(sum.real / N, sum.imag / N);
//            ;
//        }
//        return signal;
//    }
//
//    // Calculates the 2D IDFT of the input spectrum
//    public int[][] fourierInverseTransform(core.Complex[][] spectrum) {
//        int numRows = spectrum.length;
//        int numCols = spectrum[0].length;
//
//        // Perform the IDFT on each column
//        core.Complex[][] colSignal = new core.Complex[numCols][numRows];
//        for (int x = 0; x < numCols; x++) {
//            core.Complex[] column = new core.Complex[numRows];
//            for (int y = 0; y < numRows; y++) {
//                column[y] = spectrum[y][x];
//            }
//            core.Complex[] colSignal1D = idft(column);
//            for (int y = 0; y < numRows; y++) {
//                colSignal[x][y] = colSignal1D[y];
//            }
//        }
//
//        // Transpose the result and perform the IDFT on each row
//        core.Complex[][] rowSignal = new core.Complex[numRows][numCols];
//        for (int y = 0; y < numRows; y++) {
//            core.Complex[] row = new core.Complex[numCols];
//            for (int x = 0; x < numCols; x++) {
//                row[x] = colSignal[x][y];
//            }
//            core.Complex[] rowSignal1D = idft(row);
//            for (int x = 0; x < numCols; x++) {
//                rowSignal[y][x] = rowSignal1D[x];
//            }
//        }
//
//        // Convert the core.Complex array to an integer array
//        int[][] image = new int[numRows][numCols];
//        for (int y = 0; y < numRows; y++) {
//            for (int x = 0; x < numCols; x++) {
//                image[y][x] = (int) rowSignal[y][x].real;
//            }
//        }
//
//        return image;
//    }
