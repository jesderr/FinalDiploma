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
    private BufferedImage finalBufferedImage;
    private final JPanel mainPanel;
    private Filter filter;
    private JComboBox<Filter> filtersComboBox;
    private JLabel jLabelForCutoff;
    private JLabel jLabelForOrder;
    private JLabel jLabelForSliderCutoff;
    private JSlider jSliderForCutoff;
    private JSlider jSliderForOrder;

    private JButton jButtonForSaveResult;

    public GuiWrapper() {
        this.mainFrame = new JFrame("Main");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setBounds(0, 0, 900, 700);
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
        this.configureSaveBtn();

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
        this.mainPanel.add(this.jButtonForSaveResult);
    }


//    public void configureSliderForOrder() {
//        this.jSliderForOrder = new JSlider(JSlider.HORIZONTAL, -5, 5, 2);
//        this.jSliderForOrder.setBounds(10, 540, 660, 50);
//        this.jSliderForOrder.setMajorTickSpacing(1);
//        this.jSliderForOrder.setPaintLabels(true);
//        this.jSliderForOrder.setPaintTicks(true);
//        this.jSliderForOrder.setSnapToTicks(true);
//        this.jLabelForOrder = new JLabel("Order = " + 2);
//        this.jLabelForOrder.setBounds(480, 400, 210, 30);
//        this.jLabelForOrder.setVisible(false);
//        this.jSliderForOrder.setVisible(false);
//    }
//
//    public void configureSliderForCutoff() {
//        this.jSliderForCutoff = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
//        this.jSliderForCutoff.setBounds(10, 440, 800, 50);
//        this.jSliderForCutoff.setMajorTickSpacing(10);
//        this.jSliderForCutoff.setPaintLabels(true);
//        this.jSliderForCutoff.setPaintTicks(true);
//        this.jSliderForCutoff.setSnapToTicks(true);
//        this.jLabelForSliderCutoff = new JLabel("slider value = " + 100);
//        this.jLabelForSliderCutoff.setVisible(false);
//        this.jLabelForSliderCutoff.setBounds(240, 400, 200, 30);
//        this.jSliderForCutoff.setVisible(false);
//    }

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
        this.jSliderForOrder.addChangeListener(eventForOrder -> {
            JSlider sourceOrder = (JSlider) eventForOrder.getSource();
            if (!sourceOrder.getValueIsAdjusting()) {
                int valueForOrder = sourceOrder.getValue();
                jLabelForOrder.setText("Order = " + valueForOrder);
            }
        });
    }

    public void configureSliderForCutoff() {
        this.jSliderForCutoff = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        this.jSliderForCutoff.setBounds(10, 440, 800, 50);
        this.jSliderForCutoff.setMajorTickSpacing(10);
        this.jSliderForCutoff.setPaintLabels(true);
        this.jSliderForCutoff.setPaintTicks(true);
        this.jSliderForCutoff.setSnapToTicks(true);
        this.jLabelForSliderCutoff = new JLabel("Slider value = " + 100);
        this.jLabelForSliderCutoff.setBounds(240, 400, 200, 30);
        this.jSliderForCutoff.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                jLabelForSliderCutoff.setText("Slider value = " + value);
            }
        });
    }

//    public void configureApplyBtn() {
//        this.applyBtn = new JButton("apply");
//        this.applyBtn.setBounds(10, 10, 70, 30);
//        this.jLabelForCutoff = new JLabel("Max cutoff = ");
//        this.jLabelForCutoff.setBounds(10, 400, 200, 30);
//        this.jLabelForCutoff.setVisible(false);
//        this.applyBtn.addActionListener(e -> {
//            jLabelForSliderCutoff.setVisible(true);
//            jLabelForCutoff.setVisible(true);
//            if (filter == Filter.BUTTERWORTH_FILTER) {
//                this.imageProcessor = new ImageProcessor(this.filter, 100, 2);
//                jSliderForOrder.setVisible(true);
//                jLabelForOrder.setVisible(true);
//                jSliderForCutoff.setValue(100);
//                jSliderForOrder.setValue(2);
//            } else {
//                jSliderForOrder.setVisible(false);
//                jLabelForOrder.setVisible(false);
//                jSliderForCutoff.setValue(100);
//                this.imageProcessor = new ImageProcessor(this.filter, 100);
//            }
//            BufferedImage resultImage = imageProcessor.process(originalBufferedImage);
//            resultImagePanel.setBufferedImage(resultImage);
//            jSliderForCutoff.setVisible(true);
//            jLabelForCutoff.setText("Max cutoff = " + this.imageProcessor.getMaxCutoff(originalBufferedImage));
//            jSliderForCutoff.setMaximum((int) this.imageProcessor.getMaxCutoff(originalBufferedImage));
//            jSliderForCutoff.addChangeListener(event -> {
//                JSlider source = (JSlider) event.getSource();
//                if (!source.getValueIsAdjusting()) {
//                    int value = source.getValue();
//                    jLabelForSliderCutoff.setText("slider value = " + value);
//                    if (filter == Filter.BUTTERWORTH_FILTER) {
//                        this.imageProcessor = new ImageProcessor(this.filter, value, 2);
//                    } else {
//                        this.imageProcessor = new ImageProcessor(this.filter, value);
//                    }
//                    BufferedImage nextResultImage = imageProcessor.process(originalBufferedImage);
//                    resultImagePanel.setBufferedImage(nextResultImage);
//                }
//            });
//            if (jSliderForOrder.isVisible()) {
//                jSliderForOrder.addChangeListener(eventForOrder -> {
//                    JSlider sourceOrder = (JSlider) eventForOrder.getSource();
//                    if (!sourceOrder.getValueIsAdjusting()) {
//                        int valueForOrder = sourceOrder.getValue();
//                        jLabelForOrder.setText("Order = " + valueForOrder);
//                        this.imageProcessor = new ImageProcessor(this.filter, jSliderForCutoff.getValue(), valueForOrder);
//                        BufferedImage nextResultImage = imageProcessor.process(originalBufferedImage);
//                        resultImagePanel.setBufferedImage(nextResultImage);
//                    }
//                });
//            }
//        });
//    }

    public void configureApplyBtn() {
        this.applyBtn = new JButton("Apply");
        this.applyBtn.setBounds(10, 10, 70, 30);
        this.jLabelForCutoff = new JLabel("Max cutoff = ");
        this.jLabelForCutoff.setBounds(10, 400, 200, 30);
        this.applyBtn.addActionListener(e -> {
            jButtonForSaveResult.setVisible(true);
            if (filter == Filter.BUTTERWORTH_FILTER) {
                this.imageProcessor = new ImageProcessor(this.filter, jSliderForCutoff.getValue(), jSliderForOrder.getValue());
                jSliderForOrder.setVisible(true);
                jLabelForOrder.setVisible(true);
            } else {
                this.imageProcessor = new ImageProcessor(this.filter, jSliderForCutoff.getValue());
            }
            finalBufferedImage = imageProcessor.process(originalBufferedImage);
            resultImagePanel.setBufferedImage(finalBufferedImage);
            jSliderForCutoff.setVisible(true);
            jLabelForCutoff.setText("Max cutoff = " + this.imageProcessor.getMaxCutoff(originalBufferedImage));
            jSliderForCutoff.setMaximum((int) this.imageProcessor.getMaxCutoff(originalBufferedImage));
            jSliderForCutoff.addChangeListener(event -> {
                JSlider source = (JSlider) event.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    jLabelForSliderCutoff.setText("Slider value = " + value);
                    if (filter == Filter.BUTTERWORTH_FILTER) {
                        this.imageProcessor = new ImageProcessor(this.filter, value, 2);
                    } else {
                        this.imageProcessor = new ImageProcessor(this.filter, value);
                    }
                    finalBufferedImage = imageProcessor.process(originalBufferedImage);
                    resultImagePanel.setBufferedImage(finalBufferedImage);
                }
            });
            if (jSliderForOrder.isVisible()) {
                jSliderForOrder.addChangeListener(eventForOrder -> {
                    JSlider sourceOrder = (JSlider) eventForOrder.getSource();
                    if (!sourceOrder.getValueIsAdjusting()) {
                        int valueForOrder = sourceOrder.getValue();
                        jLabelForOrder.setText("Order = " + valueForOrder);
                        this.imageProcessor = new ImageProcessor(this.filter, jSliderForCutoff.getValue(), valueForOrder);
                        finalBufferedImage = imageProcessor.process(originalBufferedImage);
                        resultImagePanel.setBufferedImage(finalBufferedImage);
                    }
                });
            }
        });
    }

    public void configureOpenFileChooserBtn() {
        this.openFileChooserBtn = new JButton("Open file chooser");
        this.openFileChooserBtn.setBounds(100, 10, 140, 30);
        this.openFileChooserBtn.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File fileImage = fileChooser.getSelectedFile();
                try {
                    originalBufferedImage = ImageIO.read(fileImage);
                    originalImagePanel.setBufferedImage(originalBufferedImage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void configureSaveBtn() {
        this.jButtonForSaveResult = new JButton("Save");
        this.jButtonForSaveResult.setBounds(440, 10, 100, 30);
        this.jButtonForSaveResult.setVisible(false);
        this.jButtonForSaveResult.addActionListener(e -> {
            imageProcessor.saveImage(finalBufferedImage);
        });
    }

    public void configureFileChooser() {
        this.fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        this.fileChooser.setDialogTitle("Select image");
        this.fileChooser.setMultiSelectionEnabled(false);
        this.fileChooser.getSelectedFile();
    }

    public void configureFilterComboBox() {
        this.filtersComboBox = new JComboBox<>(Filter.values());
        this.filtersComboBox.setEditable(false);
        this.filtersComboBox.setBounds(260, 10, 160, 30);
        this.filtersComboBox.addActionListener(e -> {
            switch ((Filter) Objects.requireNonNull(filtersComboBox.getSelectedItem())) {
                case HIGH_PASS_FILTER -> {
                    filter = Filter.HIGH_PASS_FILTER;
                    jSliderForOrder.setVisible(false);
                    jLabelForOrder.setVisible(false);
                }
                case LOW_PASS_FILTER -> {
                    filter = Filter.LOW_PASS_FILTER;
                    jSliderForOrder.setVisible(false);
                    jLabelForOrder.setVisible(false);
                }
                case BUTTERWORTH_FILTER -> {
                    filter = Filter.BUTTERWORTH_FILTER;
                    jSliderForOrder.setVisible(true);
                    jLabelForOrder.setVisible(true);
                }
                default -> System.out.println("Mistake");
            }
        });
    }
}
