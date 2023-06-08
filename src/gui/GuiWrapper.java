package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import core.impl.Filter;
import core.impl.ImageProcessor;

public class GuiWrapper {
    private final JFrame mainFrame;
    private ImageProcessor imageProcessor;
    private JFileChooser fileChooser;
    private JButton applyBtn;
    private JButton openFileChooserBtn;
    private final ImagePanel originalImagePanel;
    private final ImagePanel resultImagePanel;
    private final SpectrumPanel fourierSpectrumPanel;
    private BufferedImage originalBufferedImage;
    private BufferedImage finalBufferedImage;
    private final JPanel mainPanel;
    private Filter filter;
    private JComboBox<Filter> filtersComboBox;
    private JLabel cutoffLabel;
    private JLabel orderLabel;
    private JLabel cutoffSliderLabel;
    private JSlider cutoffSlider;
    private JSlider orderSlider;

    private JButton jButtonForSaveResult;

    public GuiWrapper() {
        this.imageProcessor = new ImageProcessor();
        this.mainFrame = new JFrame("Main");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setBounds(0, 0, 1100, 700);
        this.mainFrame.setVisible(true);
        this.mainFrame.setResizable(false);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);

        this.configureApplyBtn();
        this.configureFileChooser();
        this.configureOpenFileChooserBtn();
        this.configureFilterComboBox();
        this.configureCutoffSlider();
        this.configureSliderForOrder();
        this.configureSaveBtn();

        this.originalImagePanel = new ImagePanel(25, 100);
        this.resultImagePanel = new ImagePanel(350, 100);
        this.fourierSpectrumPanel = new SpectrumPanel(650, 100);

        this.mainPanel.add(this.applyBtn);
        this.mainPanel.add(this.openFileChooserBtn);
        this.mainPanel.add(this.fileChooser);
        this.mainPanel.add(this.originalImagePanel);
        this.mainPanel.add(this.resultImagePanel);
        this.mainPanel.add(this.fourierSpectrumPanel);
        this.mainFrame.add(this.mainPanel);
        this.mainPanel.add(this.filtersComboBox);
        this.mainPanel.add(this.cutoffLabel);
        this.mainPanel.add(this.cutoffSlider);
        this.mainPanel.add(this.cutoffSliderLabel);
        this.mainPanel.add(this.orderSlider);
        this.mainPanel.add(this.orderLabel);
        this.mainPanel.add(this.jButtonForSaveResult);
    }

    public void configureSliderForOrder() {
        this.orderSlider = new JSlider(JSlider.HORIZONTAL, -5, 5, 2);
        this.orderSlider.setBounds(10, 540, 660, 50);
        this.orderSlider.setMajorTickSpacing(1);
        this.orderSlider.setPaintLabels(true);
        this.orderSlider.setPaintTicks(true);
        this.orderLabel = new JLabel("Order = " + 2);
        this.orderLabel.setBounds(480, 400, 210, 30);
        this.orderLabel.setVisible(false);
        this.orderSlider.setVisible(false);
        this.orderSlider.addChangeListener(eventForOrder -> {
            JSlider sourceOrder = (JSlider) eventForOrder.getSource();
            if (!sourceOrder.getValueIsAdjusting()) {
                int orderValue = sourceOrder.getValue();
                orderLabel.setText("Order = " + orderValue);
                finalBufferedImage = imageProcessor.process(originalBufferedImage, filter, cutoffSlider.getValue(),
                        orderValue);
                resultImagePanel.setBufferedImage(finalBufferedImage);
            }
        });
    }

    public void configureCutoffSlider() {
        this.cutoffSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        this.cutoffSlider.setBounds(10, 440, 800, 50);
        this.cutoffSliderLabel = new JLabel("Slider value = " + 100);
        this.cutoffSliderLabel.setBounds(240, 400, 200, 30);
        this.cutoffSlider.addChangeListener(event -> {
            JSlider source = (JSlider) event.getSource();
            if (!source.getValueIsAdjusting()) {
                int cutoffValue = source.getValue();
                cutoffSliderLabel.setText("Slider value = " + cutoffValue);
                finalBufferedImage = imageProcessor.process(originalBufferedImage, filter, cutoffValue,
                        orderSlider.getValue());
                resultImagePanel.setBufferedImage(finalBufferedImage);
            }
        });
    }

    public void configureCutoffSliderTicks() {
        double maxCutoff = imageProcessor.getMaxCutoff(originalBufferedImage);
        this.cutoffSlider.setVisible(true);
        this.cutoffLabel.setText("Max cutoff = " + maxCutoff);
        this.cutoffSlider.setMaximum((int) maxCutoff);
        this.cutoffSlider.setMajorTickSpacing((int) maxCutoff / 10);
        this.cutoffSlider.setPaintLabels(true);
        this.cutoffSlider.setPaintTicks(true);
    }

    public void configureApplyBtn() {
        this.applyBtn = new JButton("Apply");
        this.applyBtn.setBounds(10, 10, 70, 30);
        this.cutoffLabel = new JLabel("Max cutoff = ");
        this.cutoffLabel.setBounds(10, 400, 200, 30);
        this.applyBtn.addActionListener(e -> {
            long start = System.currentTimeMillis();
            jButtonForSaveResult.setVisible(true);
            if (filter == Filter.BUTTERWORTH_FILTER) {
                orderSlider.setVisible(true);
                orderLabel.setVisible(true);
            }
            finalBufferedImage = imageProcessor.process(originalBufferedImage, filter,
                    cutoffSlider.getValue(), orderSlider.getValue());
            resultImagePanel.setBufferedImage(finalBufferedImage);
            long finish = System.currentTimeMillis();
            System.out.println("Proshlo : " + (finish - start));
            // TODO: think about it
            //double[][] spectrum = imageProcessor.calculateSpectrum(originalBufferedImage);
            //fourierSpectrumPanel.setSpectrum(spectrum);
            //fourierSpectrumPanel.setBufferedImage(originalBufferedImage);

            this.configureCutoffSliderTicks();
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
                    orderSlider.setVisible(false);
                    orderLabel.setVisible(false);
                }
                case LOW_PASS_FILTER -> {
                    filter = Filter.LOW_PASS_FILTER;
                    orderSlider.setVisible(false);
                    orderLabel.setVisible(false);
                }
                case BUTTERWORTH_FILTER -> {
                    filter = Filter.BUTTERWORTH_FILTER;
                    orderSlider.setVisible(true);
                    orderLabel.setVisible(true);
                }
                default -> System.out.println("Mistake");
            }
        });
    }
}
