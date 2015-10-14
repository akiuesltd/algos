package com.akieus.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author aks
 * @since 07/10/15
 */
public class FirstFileRead {

    public static void main(String... args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("/home/aks/tmp/test.data", "rw");
        FileChannel channel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        int read = channel.read(byteBuffer);
        while (read != -1) {
//            System.out.println("Read: " + read);
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }

            byteBuffer.clear();
            read = channel.read(byteBuffer);
        }

        file.close();
    }

}
