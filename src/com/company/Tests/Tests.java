package com.company.Tests;

import com.company.MainJFrame;

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