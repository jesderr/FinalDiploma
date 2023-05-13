package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import core.Filter;
import core.ImageProcessor;

public class GuiWrapper {
    private final JFrame mainFrame;
    private ImageProcessor imageProcessor;
    private JFileChooser fileChooser;
    private JButton applyBtn;
    private JButton openFileChooserBtn;
    private final ImagePanel originalImagePanel;
    private final ImagePanel resultImagePanel;
    private BufferedImage originalBufferedImage;
    private final JPanel mainPanel;
    private Filter filter;
    private JComboBox<Filter> filtersComboBox;
    private JLabel jLabelForCutoff;
    private JLabel jLabelForOrder;
    private JLabel jLabelForSliderCutoff;
    private JSlider jSliderForCutoff;
    private JSlider jSliderForOrder;

    public GuiWrapper() {
        this.mainFrame = new JFrame("Main");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setBounds(0, 0, 700, 700);
        this.mainFrame.setVisible(true);
        this.mainFrame.setResizable(false);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);

        this.configureApplyBtn();
        this.configureFileChooser();
        this.configureOpenFileChooserBtn();
        this.configureFilterComboBox();
        this.configureSliderForCutoff();
        this.configureSliderForOrder();

        this.originalImagePanel = new ImagePanel(25, 100);
        this.resultImagePanel = new ImagePanel(350, 100);

        this.mainPanel.add(this.applyBtn);
        this.mainPanel.add(this.openFileChooserBtn);
        this.mainPanel.add(this.fileChooser);
        this.mainPanel.add(this.originalImagePanel);
        this.mainPanel.add(this.resultImagePanel);
        this.mainFrame.add(this.mainPanel);
        this.mainPanel.add(this.filtersComboBox);
        this.mainPanel.add(this.jLabelForCutoff);
        this.mainPanel.add(this.jSliderForCutoff);
        this.mainPanel.add(this.jLabelForSliderCutoff);
        this.mainPanel.add(this.jSliderForOrder);
        this.mainPanel.add(this.jLabelForOrder);

    }


    public void configureSliderForOrder() {
        this.jSliderForOrder = new JSlider(JSlider.HORIZONTAL, -5, 5, 2);
        this.jSliderForOrder.setBounds(10, 540, 660, 50);
        this.jSliderForOrder.setMajorTickSpacing(1);
        this.jSliderForOrder.setPaintLabels(true);
        this.jSliderForOrder.setPaintTicks(true);
        this.jSliderForOrder.setSnapToTicks(true);
        this.jLabelForOrder = new JLabel("Order = " + 2);
        this.jLabelForOrder.setBounds(480, 400, 210, 30);
        this.jLabelForOrder.setVisible(false);
        this.jSliderForOrder.setVisible(false);
//        this.jSliderForOrder.addChangeListener(eventForOrder ->{
//            JSlider sourceOrder = (JSlider) eventForOrder.getSource();
//            if(!sourceOrder.getValueIsAdjusting()){
//                int valueForOrder = sourceOrder.getValue();
//                jLabelForOrder.setText("Order = " + valueForOrder);
//            }
//        });
//        if (jSliderForOrder.isVisible()) {
//            jSliderForOrder.addChangeListener(eventForOrder -> {
//                JSlider sourceOrder = (JSlider) eventForOrder.getSource();
//                if (!sourceOrder.getValueIsAdjusting()) {
//                    int valueForOrder = sourceOrder.getValue();
//                    jLabelForOrder.setText("Order = " + valueForOrder);
//                    this.imageProcessor = new ImageProcessor(this.filter, value, valueForOrder);
//                }
//            });
//        }
    }

    public void configureSliderForCutoff() {
        this.jSliderForCutoff = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        this.jSliderForCutoff.setBounds(10, 440, 660, 50);
        this.jSliderForCutoff.setMajorTickSpacing(10);
        this.jSliderForCutoff.setPaintLabels(true);
        this.jSliderForCutoff.setPaintTicks(true);
        this.jSliderForCutoff.setSnapToTicks(true);
        this.jLabelForSliderCutoff = new JLabel("slider value = " + 100);
        this.jLabelForSliderCutoff.setVisible(false);
        this.jLabelForSliderCutoff.setBounds(240, 400, 200, 30);
        this.jSliderForCutoff.setVisible(false);
    }

    public void configureApplyBtn() {
        this.applyBtn = new JButton("apply");
        this.applyBtn.setBounds(10, 10, 70, 30);
        this.jLabelForCutoff = new JLabel("Max cutoff = ");
        this.jLabelForCutoff.setBounds(10, 400, 200, 30);
        this.applyBtn.addActionListener(e -> {
            jLabelForSliderCutoff.setVisible(true);
            if (filter == Filter.BUTTERWORTH_FILTER) {
                this.imageProcessor = new ImageProcessor(this.filter, 100, 2);
//                jSliderForOrder.setVisible(true);
                jLabelForOrder.setVisible(true);
            } else {
                this.imageProcessor = new ImageProcessor(this.filter, 100);
            }
            BufferedImage resultImage = imageProcessor.process(originalBufferedImage);
            resultImagePanel.setBufferedImage(resultImage);
            resultImagePanel.repaint();
            jSliderForCutoff.setVisible(true);
            jLabelForCutoff.setText("Max cutoff = " + this.imageProcessor.getMaxCutoff(originalBufferedImage));
            jSliderForCutoff.setMaximum((int) this.imageProcessor.getMaxCutoff(originalBufferedImage));
            jSliderForCutoff.addChangeListener(event -> {
                JSlider source = (JSlider) event.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    jLabelForSliderCutoff.setText("slider value = " + value);
                    if (filter == Filter.BUTTERWORTH_FILTER) {
                        this.imageProcessor = new ImageProcessor(this.filter, value, 2);
                    } else {
                        this.imageProcessor = new ImageProcessor(this.filter, value);
                    }
                    BufferedImage nextResultImage = imageProcessor.process(originalBufferedImage);
                    resultImagePanel.setBufferedImage(nextResultImage);
                    resultImagePanel.repaint();
                }
            });
        });
    }

    public void configureOpenFileChooserBtn() {
        this.openFileChooserBtn = new JButton("open file chooser");
        this.openFileChooserBtn.setBounds(100, 10, 120, 30);
        this.openFileChooserBtn.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileImage = fileChooser.getSelectedFile();
                try {
                    originalBufferedImage = ImageIO.read(fileImage);
                    originalImagePanel.setBufferedImage(originalBufferedImage);
                    originalImagePanel.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void configureFileChooser() {
        this.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        this.fileChooser.setDialogTitle("Select image");
        this.fileChooser.setMultiSelectionEnabled(false);
        this.fileChooser.getSelectedFile();
    }

    public void configureFilterComboBox() {
//        String[] filters = {
//                Filter.BUTTERWORTH_FILTER.toString(),
//                Filter.HIGH_PASS_FILTER.toString(),
//                Filter.LOW_PASS_FILTER.toString()
//        };
        this.filtersComboBox = new JComboBox<>(Filter.values());
        this.filtersComboBox.setEditable(false);
        this.filtersComboBox.setBounds(250, 10, 140, 30);
        this.filtersComboBox.addActionListener(e -> {
//            filter = Filter.valueOf(e.getActionCommand());
            switch ((Filter) Objects.requireNonNull(filtersComboBox.getSelectedItem())) {
                case HIGH_PASS_FILTER -> filter = Filter.HIGH_PASS_FILTER;

                case LOW_PASS_FILTER -> filter = Filter.LOW_PASS_FILTER;
                case BUTTERWORTH_FILTER -> filter = Filter.BUTTERWORTH_FILTER;
                default -> System.out.println("Mistake");
            }
        });
//        switch ((Filter) Objects.requireNonNull(filtersComboBox.getSelectedItem())){
//            case HIGH_PASS_FILTER -> filter = Filter.HIGH_PASS_FILTER;
//            case LOW_PASS_FILTER -> filter = Filter.LOW_PASS_FILTER;
//            case BUTTERWORTH_FILTER -> filter = Filter.BUTTERWORTH_FILTER;
//            default -> System.out.println("Mistake");
//        }
    }
}
