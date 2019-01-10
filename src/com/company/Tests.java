package com.company;

import java.awt.EventQueue;

public class Tests {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainJFrame();
            }
        });
    }
}