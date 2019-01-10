package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.*;
/**
 *
 * @author Malgorzata
 */
public class MainJFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private Image image;
    private BufferedImage newImage;
    private File file;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private ApplyFilter task;

    public MainJFrame() {
        initComponents();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int progress = task.getProgress();
        jProgressBar1.setValue(progress);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButtonFilter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar(0,100);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Małgorzata Skrzypecka");
        setMinimumSize(new java.awt.Dimension(650, 650));

        jButton1.setText("Wczytaj plik");
        jButton1.setMinimumSize(new java.awt.Dimension(89, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, BorderLayout.WEST);

        jLabel1.setText("<tu będzie wczytany obraz>");
        jLabel1.setMaximumSize(new java.awt.Dimension(140, 50));
        jLabel1.setMinimumSize(new java.awt.Dimension(140, 50));
        jLabel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel1ComponentResized(evt);
            }
        });
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);

        jButtonFilter.setText("Nałóż filtr");
        jButtonFilter.setEnabled(false);
        jButtonFilter.setMaximumSize(new java.awt.Dimension(140, 50));
        jButtonFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFilterActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonFilter, BorderLayout.EAST);

        jProgressBar1.setMaximumSize(new java.awt.Dimension(140, 50));
        jProgressBar1.setValue(0);
        jProgressBar1.setStringPainted(true);
        getContentPane().add(jProgressBar1, BorderLayout.AFTER_LAST_LINE);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFilterActionPerformed(ActionEvent evt) {
        jButtonFilter.setEnabled(false);

        task = new ApplyFilter(file, this.jLabel1, jLabel1.getWidth(), jLabel1.getHeight());
        task.addPropertyChangeListener(this);
        task.execute();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter("Grafika JPG", "jpg");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            file = fileChooser.getSelectedFile();
            java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
            image = toolkit.getImage(file.toString());
            jLabel1.setText("");
            int labelWidth = jLabel1.getWidth();
            int labelHeight = jLabel1.getHeight();

            Image imageTemp = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imageTemp);
            jLabel1.setIcon(imageIcon);
            jButtonFilter.setEnabled(true);
            jProgressBar1.setValue(0);
        }
    }

    private void jLabel1ComponentResized(java.awt.event.ComponentEvent evt) {
        if(image!=null)
        {
            int labelWidth = jLabel1.getWidth();
            int labelHeight = jLabel1.getHeight();
            Image imageTemp= image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imageTemp);
            jLabel1.setIcon(imageIcon);
        }
    }
}
