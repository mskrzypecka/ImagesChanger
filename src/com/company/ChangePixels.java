package com.company;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

class ChangePixels implements Callable<Integer> {
    private Coords coords;
    private BufferedImage image;

    public ChangePixels(Coords coords, BufferedImage image){
        this.coords = coords;
        this.image = image;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        System.out.println("Poczatek dla X: " + coords.minX + ", Y: " + coords.minY );
        for (int y = coords.minY; y <= coords.maxY; y++) {
            for (int x = coords.minX; x < coords.maxX; x++) {
                try {
                    int p = image.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    //subtract RGB from 255
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    //set new RGB value
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, p);
                } catch(Exception ex) {
                    System.out.println("Poza zasiegiem");
                }
            }
        }
        System.out.println("Koniec dla X: " + coords.minX + ", Y: " + coords.minY );
        return 1;
    }
}