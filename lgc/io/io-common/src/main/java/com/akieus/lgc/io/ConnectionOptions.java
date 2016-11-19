package com.akieus.lgc.io;

import java.net.SocketException;
import java.nio.channels.SocketChannel;

/**
 * Created by aks on 18/03/2016.
 */
public class ConnectionOptions {

    private int receiveBufferSize;
    private int sendBufferSize;
    private int linger;
    private boolean tcpNoDelay;
    private boolean keepAlive;

    public ConnectionOptions(int receiveBufferSize, int sendBufferSize, int linger, boolean tcpNoDelay, boolean keepAlive) {
        this.receiveBufferSize = receiveBufferSize;
        this.sendBufferSize = sendBufferSize;
        this.linger = linger;
        this.tcpNoDelay = tcpNoDelay;
        this.keepAlive = keepAlive;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public int getLinger() {
        return linger;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void configure(SocketChannel channel) throws SocketException {
        if (receiveBufferSize != 0) channel.socket().setReceiveBufferSize(receiveBufferSize);
        if (sendBufferSize != 0) channel.socket().setSendBufferSize(sendBufferSize);
        if (linger != 0) channel.socket().setSoLinger(true, linger);

        channel.socket().setTcpNoDelay(tcpNoDelay);
        channel.socket().setKeepAlive(keepAlive);
    }

    public static ConnectionOptions defaults() {
        return ConnectionOptionsBuilder.newBuilder().build();
    }

    public static class ConnectionOptionsBuilder {
        private int receiveBufferSize;
        private int sendBufferSize;
        private int linger = 5;
        private boolean tcpNoDelay;
        private boolean keepAlive;

        private ConnectionOptionsBuilder() {
        }

        public static ConnectionOptionsBuilder newBuilder() {
            return new ConnectionOptionsBuilder();
        }

        public ConnectionOptionsBuilder withReceiveBufferSize(int receiveBufferSize) {
            this.receiveBufferSize = receiveBufferSize;
            return this;
        }

        public ConnectionOptionsBuilder withSendBufferSize(int sendBufferSize) {
            this.sendBufferSize = sendBufferSize;
            return this;
        }

        public ConnectionOptionsBuilder withLinger(int linger) {
            this.linger = linger;
            return this;
        }

        public ConnectionOptionsBuilder withTcpNoDelay(boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
            return this;
        }

        public ConnectionOptionsBuilder withKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public ConnectionOptionsBuilder defaultConnectionOptions() {
            return newBuilder()
                    .withReceiveBufferSize(receiveBufferSize)
                    .withSendBufferSize(sendBufferSize)
                    .withLinger(linger)
                    .withTcpNoDelay(tcpNoDelay)
                    .withKeepAlive(keepAlive);
        }

        public ConnectionOptionsBuilder from(ConnectionOptions source) {
            return newBuilder()
                    .withReceiveBufferSize(source.getReceiveBufferSize())
                    .withSendBufferSize(source.getSendBufferSize())
                    .withLinger(source.getLinger())
                    .withTcpNoDelay(source.isTcpNoDelay())
                    .withKeepAlive(source.isKeepAlive());
        }

        public ConnectionOptions build() {
            return new ConnectionOptions(receiveBufferSize, sendBufferSize, linger, tcpNoDelay, keepAlive);
        }
    }
}
