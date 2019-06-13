package com.xxl.module.media.audio;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author xxl
 * @date 19/6/13.
 * <p>
 * Description
 **/
public class Wav2Pcm {
    /**
     * WAV转PCM文件
     *
     * @param wavFilePath wav文件路径
     * @param pcmFilePath pcm要保存的文件路径及文件名
     * @return
     */
    public String convertAudioFile(String wavFilePath, String pcmFilePath) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        try {
            fileInputStream = new FileInputStream(wavFilePath);
            fileOutputStream = new FileOutputStream(pcmFilePath);
            byte[] wavbyte = inputStreamToByte(fileInputStream);

            //去除前44 个字节 则是PCM 文件
            byte[] pcmbyte = Arrays.copyOfRange(wavbyte, 44, wavbyte.length);
            fileOutputStream.write(pcmbyte);
            closeStream(fileInputStream, fileOutputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return pcmFilePath;
    }

    public static short[] bytesToShort(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        short[] shorts = new short[bytes.length / 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }

    private static void closeStream(Closeable... closeables) {
        try {
            for (Closeable closeable : closeables) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入流转byte二进制数据
     *
     * @param fis
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToByte(FileInputStream fis) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        long size = fis.getChannel().size();
        byte[] buffer = null;
        if (size <= Integer.MAX_VALUE) {
            buffer = new byte[(int) size];
        } else {
            buffer = new byte[8];
            for (int ix = 0; ix < 8; ++ix) {
                int offset = 64 - (ix + 1) * 8;
                buffer[ix] = (byte) ((size >> offset) & 0xff);
            }
        }
        int len;
        while ((len = fis.read(buffer)) != -1) {
            byteStream.write(buffer, 0, len);
        }
        byte[] data = byteStream.toByteArray();
        closeStream(byteStream);
        return data;
    }
}
