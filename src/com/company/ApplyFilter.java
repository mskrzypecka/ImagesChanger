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
        ExecutorService service =  Executors.newFixedThreadPool(2);
        List<Future<Integer>> taskList = new ArrayList<>();
        int difX = image.getWidth()/9;
        int difY = image.getHeight()/9;

        for(int i=0; i<9; i++) {
            Coords coord = new Coords(i*difX, i*difX+difX, i*difY, i*difY + difY);
            Callable<Integer> pix = new ChangePixels(coord, image);
            Future<Integer> result = service.submit(pix);
            taskList.add(result);
        }

        while (this.progress < 100) {
            int sum = 0;
            for(Future<Integer> task : taskList){
                sum += task.get();
            }
            this.progress = sum * 11;
            Thread.sleep(500);
            this.setProgress(this.progress);
        }
        this.processedImage = image;

        return this.progress;
    }


    @Override
    protected void done() {
        Image imageTemp = this.processedImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        this.imageContainer.setIcon(new ImageIcon(imageTemp));
        this.progress = 100;
        this.setProgress(this.progress);
    }
}
