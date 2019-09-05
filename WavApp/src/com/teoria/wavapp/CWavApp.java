package com.teoria.wavapp;

import javax.swing.*;

public class CWavApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame Ventana = new WavAppForm();
                Ventana.setVisible(true);
                Ventana.setSize(700,600);
            }
        });
    }
}
