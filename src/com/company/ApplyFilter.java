package com.company;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.concurrent.*;
import java.util.concurrent.Callable;

public class ApplyFilter extends SwingWorker<Integer, Integer> {
    public static final int THREADS_ROOT = 3;
    public static int THREADS = THREADS_ROOT*THREADS_ROOT;
    private int progress;
    private int labelWidth, labelHeight;
    private File imageFile;
    private JLabel imageContainer;
    private BufferedImage processedImage;

    public ApplyFilter(File imageFile, JLabel imageContainer, int width, int height) {
        this.imageFile = imageFile;
        this.imageContainer = imageContainer;
        labelWidth = width;
        labelHeight = height;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        BufferedImage image = ImageIO.read(imageFile);
        ExecutorService service =  Executors.newFixedThreadPool(3);
        List<Callable<Integer>> taskList = new ArrayList<>();
        List<Future<Integer>> resultList;

        System.out.println("Wymiar obrazka: x:" + image.getWidth() + ", y:" + image.getHeight());
        int difX = image.getWidth()/THREADS_ROOT;
        int difY = image.getHeight()/THREADS_ROOT;

        for(int i=0; i<THREADS_ROOT; i++) {
            for(int j=0; j<THREADS_ROOT; j++) {
                Coords coord = new Coords(i * difX, i * difX + difX, j * difY, j * difY + difY);
                taskList.add(new ChangePixels(coord, image));
            }
        }
        resultList = service.invokeAll(taskList);

        int sum = 0;
        while (THREADS != sum) {
            sum = 0;
            for(Future<Integer> task : resultList){
                sum += task.isDone() ? 1 : 0;
            }
            this.progress = sum * (100/THREADS);
            this.setProgress(this.progress);
        }
        service.shutdown();
        this.processedImage = image;

        return this.progress;
    }

    @Override
    protected void done() {
        System.out.println("done function");
        Image imageTemp = this.processedImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        this.imageContainer.setIcon(new ImageIcon(imageTemp));
        this.progress = 100;
        this.setProgress(this.progress);
    }
}
