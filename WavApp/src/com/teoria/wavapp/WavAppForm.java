package com.teoria.wavapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WavAppForm extends JFrame {
    private JTextField textField1;
    private JPanel panel1;
    private JButton examinarButton;
    private JButton validarButton;
    private JLabel lblChunkID;
    private JLabel lblChunkSize;
    private JLabel lblFormat;
    private JLabel lblSubChunk1ID;
    private JLabel lblSubChunk1Size;
    private JLabel lblAudioFormat;
    private JLabel lblNumChannels;
    private JLabel lblSampleRate;
    private JLabel lblByteRate;
    private JLabel lblBlockAlign;
    private JLabel lblBitsPerSample;
    private JLabel lblSubChunk2ID;
    private JLabel lblSubChunk2Size;
    private JLabel lblError;
    private JButton salirButton;

    private String ruta;

    public WavAppForm() {
        super("WavApp");
        setContentPane(panel1);

        examinarButton.addActionListener(new ActionListener() { //Boton de examinar, mediante el cual obtengo la ruta del archivo wav
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser();
                jfc.showOpenDialog(jfc);  // Ventana de dialogo para ubicar el archivo
                ruta = jfc.getSelectedFile().getAbsolutePath(); //almaceno la ruta del archivo en "ruta"
                textField1.setText(ruta);
                validarButton.setEnabled(true);


            }
        });
        validarButton.addActionListener(new ActionListener() { // Valida y llena los campos si el archivo es wav
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lblError.setText("");
                CHeaderReader hr; // hr es una referencia a un objeto de CHeaderReader, mediante la cual se hace la validacion y lectura
                CWavHeader header; // Instancia de la clase CWavHeader
                try{
                    hr = new CHeaderReader(ruta);  // instancio hr usando la ruta obtenida anteriormente
                    header = hr.read(); // Ejecuto el metodo read del objeto hr, el cual devolvera una instancia a CWavHeader si el archivo leido es wav o null si no
                    if (header != null) { // Si es != null el archivo es wav. Cargo los labels con los datos del objeto
                        lblChunkID.setText(new String(header.getChunkID()));
                        lblChunkSize.setText(String.format("%d", header.getChunkSize()));
                        lblFormat.setText(new String(header.getFormat()));
                        lblSubChunk1ID.setText(new String(header.getSubChunk1ID()));
                        lblSubChunk1Size.setText(String.format("%d", header.getSubChunk1Size()));
                        lblAudioFormat.setText(String.format("%d", header.getAudioFormat()));
                        lblNumChannels.setText(String.format("%d", header.getNumChannels()));
                        lblSampleRate.setText(String.format("%d", header.getSampleRate()));
                        lblByteRate.setText(String.format("%d", header.getByteRate()));
                        lblBlockAlign.setText(String.format("%d", header.getBlockAlign()));
                        lblBitsPerSample.setText(String.format("%d", header.getBitsPerSample()));
                        lblSubChunk2ID.setText(new String(header.getSubChunk2ID()));
                        lblSubChunk2Size.setText(String.format("%d", header.getSubChunk2Size()));
                    }
                    else lblError.setText("El Archivo no es wav"); // Si no es wav muestro mensaje de error
                }
                catch (IOException e){
                    lblError.setText(e.getMessage());
                }

            }
        });
        salirButton.addActionListener(new ActionListener() { //Metodo para detener la ejecucion de la aplicacion al hacer click en salir
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                System.exit(0);
            }
        });
    }
}
