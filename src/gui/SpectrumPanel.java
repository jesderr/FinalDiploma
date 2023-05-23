package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpectrumPanel extends JPanel {
    private BufferedImage spectrumImage;
    private double[][] spectrum;
    private final int x;
    private final int y;

    public SpectrumPanel(int x, int y) {
        this.setLayout(null);
        this.x = x;
        this.y = y;
    }

    public void setSpectrum(double[][] spectrum) {
        this.spectrum = spectrum;
    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.spectrumImage = bufferedImage;
        int scaledWidth = 250;
        int scaledHeight = 250;
        this.setBounds(this.x, this.y, scaledWidth, scaledHeight);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.spectrumImage.getWidth();
        int height = this.spectrumImage.getHeight();
        // Отображение исходного изображения
        g.drawImage(this.spectrumImage, 0, 0, null);

        // Отображение Фурье-спектра
        int scale = 1; // Масштабирование спектра
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int intensity = (int) (this.spectrum[y][x] * scale);
                g.setColor(new Color(intensity, intensity, intensity));
                g.fillRect(x, y, 1, 1);
            }
        }
    }
}
