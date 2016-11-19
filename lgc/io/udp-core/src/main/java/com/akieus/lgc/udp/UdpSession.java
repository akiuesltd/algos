package com.akieus.lgc.udp;

import com.akieus.lgc.io.MessageListener;
import com.akieus.lgc.io.ByteBufferProvider;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
public class UdpSession {
    private static final Logger LOG = getLogger(TcpSession.class);

    private final SocketChannel channel;
    private final ByteBuffer byteBuffer;
    private final MessageListener messageListener;

    public UdpSession(SocketChannel channel, ByteBufferProvider byteBufferProvider, MessageListener messageListener) {
        this.channel = channel;
        this.messageListener = messageListener;
        this.byteBuffer = byteBufferProvider.get();
    }

    void read() throws IOException {
        if (byteBuffer.remaining() == 0) {
            throw new InconsistentBufferState();
        }
        int read = channel.read(byteBuffer);
        if (read == -1) {
            throw new ClosedChannelException();
        } else if (read == 0) {
            throw new InconsistentBufferState();
        } else {
            try {
                messageListener.onMessage(byteBuffer);
            } catch (Exception e) {
                throw new InconsistentBufferState(e);
            }
        }
    }

    public void write(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
    }

    public void close() {
        if (channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException e) {
            }
        }
    }

    static class InconsistentBufferState extends IOException {
        public InconsistentBufferState() {
        }

        public InconsistentBufferState(Throwable cause) {
            super(cause);
        }
    }
}
