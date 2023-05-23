package core;

import java.awt.image.BufferedImage;

public interface FourierActions {
    double[][] calculateSpectrum(BufferedImage image);
    Complex[][] fourierTransform(int[][] input);
    int[][] fourierInverseTransform(Complex[][] signals);
}
