package com.company;

import javax.swing.*;

public class ApplyFilter extends SwingWorker<Integer, Integer> {
    private int progress = 0;

    @Override
    protected Integer doInBackground() throws Exception {


//        while (this.progress < 100) {
//            this.progress += 10;
//            Thread.sleep(500);
//            this.setProgress(this.progress);
//        }
        return this.progress;
    }

    @Override
    protected void done() {
        this.progress = 100;
    }
}
