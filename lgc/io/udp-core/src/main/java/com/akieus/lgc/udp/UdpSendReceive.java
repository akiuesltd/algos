package com.akieus.lgc.udp;

import com.akieus.lgc.io.ByteBufferProvider;
import com.akieus.lgc.io.DefaultByteBufferProvider;
import com.akieus.lgc.io.MessageListener;
import com.akieus.lgc.util.IOUtils;
import com.akieus.lgc.util.Misc;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
public class UdpSendReceive {
    private static final Logger LOG = getLogger(UdpSendReceive.class);

    private static final String TEST_SERVER = "TEST_SERVER";
    private static final String TEST_CLIENT = "TEST_CLIENT";

    public void testSendReceive() throws InterruptedException, IOException {
        ByteBufferProvider bufferProvider = new DefaultByteBufferProvider(1024, false);
        CountDownLatch serverReceivedMsg = new CountDownLatch(1);
        MessageListener serverListener = (byteBuffer) -> serverReceivedMsg.countDown();


        String address = "239.255.0.0";
        int port = IOUtils.findFreePort();
        UdpSource server = new UdpSource(TEST_SERVER, address, port, "lo", serverListener, bufferProvider);
        server.start();
        Misc.sleepSafely(100, MILLISECONDS);

        final AtomicReference<UdpSession> clientSession = new AtomicReference<>();
        CountDownLatch clientConnected = new CountDownLatch(1);
        CountDownLatch clientReceivedMsg = new CountDownLatch(1);
        MessageListener clientListener = (byteBuffer) -> clientReceivedMsg.countDown();
        UdpSource client = new UdpSource(TEST_CLIENT, address, port, "lo", clientListener, bufferProvider);
        client.addListener(new ConnectionListener() {
            @Override
            public void connected(UdpSession session) {
                LOG.info("Client connected, session={}", session);
                clientSession.set(session);
                clientConnected.countDown();
            }

            @Override
            public void disconnected(UdpSession session) {

            }
        });
        client.start();
        if(!clientConnected.await(100, MILLISECONDS)) {
            throw new RuntimeException("Didn't get clientConnect");
        }

        ByteBuffer buffer = bufferProvider.get();
        clientSession.get().write(buffer.putLong(1L));
        if (serverReceivedMsg.await(100, MILLISECONDS)) {
            LOG.info("Received message...");
        } else {
            throw new RuntimeException("Server didn't receive a msg");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new UdpSendReceive().testSendReceive();
    }
}
