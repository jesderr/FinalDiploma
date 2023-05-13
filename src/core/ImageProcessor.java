package core;

import java.awt.image.BufferedImage;

public class ImageProcessor {
    private final TransferImage transferImage;
    private final FourierService fourier;
    private final Amplitudes ampl;
    private final FilterService filterService;
    private Filter filter;
    private double cutoff;
    private int order;

    public ImageProcessor(Filter filter, double cutoff, int order) {
        this.transferImage = new TransferImage();
        this.fourier = new FourierService();
        this.ampl = new Amplitudes();
        this.filterService = new FilterService();
        this.filter = filter;
        this.cutoff = cutoff;
        this.order = order;
    }
    public ImageProcessor(Filter filter, double cutoff) {
        this.transferImage = new TransferImage();
        this.fourier = new FourierService();
        this.ampl = new Amplitudes();
        this.filterService = new FilterService();
        this.filter = filter;
        this.cutoff = cutoff;
    }

    public double getMaxCutoff(BufferedImage image){
        this.cutoff =  this.filterService.getMaxDistanse(image);
        return this.cutoff;
    }


    public BufferedImage process(BufferedImage bufferedImage) {
        int[][] grayPixels = transferImage.pixelsToGray(bufferedImage);
        Complex[][] fourierImage = fourier.fourierTransform(grayPixels);
        Complex[][] fourierImageFFT;
        // rewrite to switch
//        switch (this.filter) {
//            case BUTTERWORTH_FILTER ->
//                    fourierImageFFT = filterService.butterworthFilter(fourierImage, this.cutoff, this.order);
//            case LOW_PASS_FILTER -> fourierImageFFT = filterService.applyLowPassFilter(fourierImage, this.cutoff);
//            case HIGH_PASS_FILTER -> fourierImageFFT = filterService.applyHighPassFilter(fourierImage, this.cutoff);
//            default -> System.out.println("Break");
//        }

        if (filter == Filter.BUTTERWORTH_FILTER) {
            fourierImageFFT = filterService.butterworthFilter(fourierImage, this.cutoff, this.order);
        } else if (filter == Filter.HIGH_PASS_FILTER) {
            fourierImageFFT = filterService.applyHighPassFilter(fourierImage,this.cutoff);
        } else {
            fourierImageFFT = filterService.applyLowPassFilter(fourierImage, this.cutoff);
        }

        int[][] result = fourier.fourierInverseTransform(fourierImageFFT);
        BufferedImage resultImage = transferImage.saveGrayImage(result);
        return resultImage;
    }
}
