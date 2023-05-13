package core;
public interface FourierActions {
    Complex[][] fourierTransform(int[][] input);
    int[][] fourierInverseTransform(Complex[][] signals);
}
