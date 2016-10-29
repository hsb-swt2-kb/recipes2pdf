package sample.util;

import com.github.vbauer.herald.annotation.Log;
import org.slf4j.Logger;

import java.io.*;

/**
 * Wrapper to load und save images.
 * Created by czoeller on 23.04.16.
 */
public class Image {

    @Log
    private Logger LOG;

    private byte[] bytes;

    /**
     * @param bytes
     */
    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Create image from InputStream.
     *
     * @param inputStream the stream to read from.
     */
    public Image(InputStream inputStream) {
        this.bytes = readBytes(inputStream);
    }

    /**
     * Save Image to file.
     *
     * @param file File to save to.
     */
    public void save(File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
            LOG.info(String.format("Saved image to %s", file.getAbsoluteFile().toPath()));
        } catch (IOException e) {
            LOG.error("IO Exception", e);
        }
    }

    /**
     * Read bytes from InputStream
     *
     * @param inputStream the stream to read from.
     * @return the bytes read.
     */
    private byte[] readBytes(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        } catch (IOException e) {
            LOG.error("IO Exception", e);
        }
        return new byte[0];
    }

    /**
     * Get Image as byte[].
     *
     * @return byte[] Bytes of the Image.
     */
    public byte[] getBytes() {
        return bytes;
    }
}
