package com.teoria.wavapp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class CHeaderReader {

    private byte[] header = new byte[44]; //Creo un buffer de tamaño de 44 bytes de longitud
    private CWavHeader wavHeader = new CWavHeader(); //Creo una instancia de la clase CWavHeader
    private InputStream is; //Defino un flujo de entrada a partir del cual voy a obtener los bytes

    public CHeaderReader (String path) throws IOException{
        is = new FileInputStream(path); // Constructor del lector del archivo, coloco el archivo de la ruta en el flujo de entrada (binario)
    }

    public CWavHeader read() throws IOException{
        int res = is.read(header); // Vuelco en el buffer "header" el flujo de entrada (el buffer se llena con los primeros 44 bytes) y devuelve la cantidad de bytes leidos
        if(res != 44) { //Si la cantidad de bytes leidos del flujo es menor a 44 es porque el archivo es incorrecto (menos de 44 bytes de longitud)
            throw new IOException("No se puede leer");
        }
        wavHeader.setChunkID(Arrays.copyOfRange(header, 0, 4));  //Asigno los primeros 4 bytes a ChunkID del objeto wavHeader
        if (new String(wavHeader.getChunkID()).compareTo("RIFF") != 0) { //Si el valor en String de esos primeros 4 bytes es != "RIFF" el archivo tiene formato incorrecto
            return null; //Devuelvo null para indicar que el archivo no es valido
        }
        wavHeader.setChunkSize(toInt(4, false)); //Leo los bytes 4 a 8 y los asigno a ChunkSize del objeto wavHeader
        wavHeader.setFormat(Arrays.copyOfRange(header, 8, 12)); // Leo los bytes 8 a 12 y los asigno a Format del objeto wavHeader
        wavHeader.setSubChunk1ID(Arrays.copyOfRange(header, 12, 16)); //Leo los bytes 12 a 16 y los asigno a Chunk1ID del objeto wavHeader
        wavHeader.setSubChunk1Size(toInt(16, false)); //Leo los bytes 16 a 20 y los asigno a Chunk1Size del objeto wavHeader
        wavHeader.setAudioFormat(toShort(20, false)); //Leo los bytes 20 a 22 y los asigno a AudioFormat del objeto wavHeader
        wavHeader.setNumChannels(toShort(22, false)); //Leo los bytes 22 a 24 y los asigno a NumChannels del objeto wavHeader
        wavHeader.setSampleRate(toInt(24, false)); //Leo los bytes 24 a 28 y los asigno a SampleRate del objeto wavHeader
        wavHeader.setByteRate(toInt(28, false)); //Leo los bytes 28 a 32 y los asigno a ByteRate del objeto wavHeader
        wavHeader.setBlockAlign(toShort(32, false)); //Leo los bytes 32 a 34 y los asigno a BlockAlign del objeto wavHeader
        wavHeader.setBitsPerSample(toShort(34, false)); //Leo los bytes 34 a 36 y los asigno a BitsPerSample del objeto wavHeader
        wavHeader.setSubChunk2ID(Arrays.copyOfRange(header, 36, 40)); //Leo los bytes 36 a 40 y los asigno a SubChunk2ID del objeto wavHeader
        wavHeader.setSubChunk2Size(toInt(40, false)); //Leo los bytes 40 a 44 y los asigno a SubChunk2Size del objeto wavHeader
        return wavHeader;
    }
    private int toInt(int start, boolean endian) { // Éste metodo se usa para convertir 4 bytes a un int
        int k = (endian) ? 1 : -1;
        if (!endian) {
            start += 3;
        }
        return (header[start] << 24) + (header[start + k * 1] << 16) +
                (header[start + k * 2] << 8) + header[start + k * 3];
    }
    private short toShort(int start, boolean endian) {  // Éste metodo se usa para convertir 2 bytes a un short
        short k = (endian) ? (short) 1 : -1;
        if (!endian) {
            start++;
        }
        return (short) ((header[start] << 8) + (header[start + k * 1]));
    }

}
