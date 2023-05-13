package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage bufferedImage;
    private final int x;
    private final int y;

    public ImagePanel(int x, int y) {
        this.setLayout(null);
        this.x = x;
        this.y = y;

    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.setBounds(this.x, this.y, this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(this.bufferedImage,0,0,this);
    }
}
