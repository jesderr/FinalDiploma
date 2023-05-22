package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage bufferedImage;
//    private Complex[][] spectrum;
    private final int x;
    private final int y;

    public ImagePanel(int x, int y) {
        this.setLayout(null);
        this.x = x;
        this.y = y;

    }

//    public ImagePanel(Complex[][] spectrum) {
//        this.spectrum = spectrum;
//        this.setLayout(null);
//    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        int scaledWidth = 250;
        int scaledHeight = 250;
        this.setBounds(this.x, this.y, scaledWidth, scaledHeight);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (this.bufferedImage != null) {
            int scaledWidth = 250;
            int scaledHeight = 250;
            g.drawImage(this.bufferedImage, 0, 0, scaledWidth, scaledHeight, null);
        }
//        g.drawImage(this.bufferedImage,0,0,this);
    }


}
