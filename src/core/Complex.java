package core;

public class Complex {
    private double real;
    private double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    public Complex add(Complex other) {
        double newReal = this.real + other.real;
        double newImaginary = this.imag + other.imag;
        return new Complex(newReal, newImaginary);
    }

    public Complex sub(Complex other) {
        double newReal = this.real - other.real;
        double newImaginary = this.imag - other.imag;
        return new Complex(newReal, newImaginary);
    }

    public Complex mul(Complex other) {
        double newReal = this.real * other.real - this.imag * other.imag;
        double newImaginary = this.real * other.imag + this.imag * other.real;
        return new Complex(newReal, newImaginary);
    }

    public Complex multiply(double scalar){
        double newReal = this.real * scalar;
        double newImaginary = this.imag * scalar;
        return new Complex(newReal,newImaginary);
    }

    public double abs(){
        return Math.sqrt(this.real*this.real + this.imag*this.imag);
    }

    public Complex copy() {
        return new Complex(this.real, this.imag);
    }

    public Complex createComplexFromPolar(double magnitude, double phase) {
        // Вычисление реальной и мнимой частей комплексного числа
        double real = magnitude * Math.cos(phase);
        double imaginary = magnitude * Math.sin(phase);

        // Создание комплексного числа с помощью метода fromPolar()
        return new Complex(real, imaginary);
    }
    public double getPhase() {
        return Math.atan2(this.imag, this.real);
    }

    public double getMagnitude() {
        return Math.sqrt(this.real * this.real + this.imag * this.imag);
    }
}
