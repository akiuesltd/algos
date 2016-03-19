package com.akieus.lgc.tcp;

import com.akieus.lgc.tcp.util.TestUtils;
import com.akieus.lgc.util.ByteBufferProvider;
import com.akieus.lgc.util.ConnectionOptions;
import com.akieus.lgc.util.DefaultByteBufferProvider;
import com.akieus.lgc.util.Misc;
import com.google.common.net.HostAndPort;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
public class TcpClientServerITest {
    private static final Logger LOG = getLogger(TcpClientServerITest.class);

    private static final String TEST_SERVER = "TEST_SERVER";
    private static final String TEST_CLIENT = "TEST_CLIENT";

    @Test
    public void testSendReceive() throws InterruptedException, IOException {
        ByteBufferProvider bufferProvider = new DefaultByteBufferProvider(1024, false);
        CountDownLatch serverReceivedMsg = new CountDownLatch(1);
        MessageListener serverListener = (byteBuffer) -> serverReceivedMsg.countDown();


        HostAndPort hostAndPort = HostAndPort.fromParts("localhost", TestUtils.findFreePort());
        TcpServer server = new TcpServer(TEST_SERVER, hostAndPort, ConnectionOptions.defaults(), serverListener, bufferProvider);
        server.start();
        Misc.sleepSafely(100, MILLISECONDS);

        final AtomicReference<TcpSession> clientSession = new AtomicReference<>();
        CountDownLatch clientConnected = new CountDownLatch(1);
        CountDownLatch clientReceivedMsg = new CountDownLatch(1);
        MessageListener clientListener = (byteBuffer) -> clientReceivedMsg.countDown();
        TcpClient client = new TcpClient(TEST_CLIENT, hostAndPort, ConnectionOptions.defaults(), clientListener, bufferProvider);
        client.addListener(new ConnectionListener() {
            @Override
            public void connected(TcpSession session) {
                LOG.info("Client connected, session={}", session);
                clientSession.set(session);
                clientConnected.countDown();
            }

            @Override
            public void disconnected(TcpSession session) {

            }
        });
        client.start();
        Assert.assertTrue(clientConnected.await(100000, MILLISECONDS));

        ByteBuffer buffer = bufferProvider.get();
        clientSession.get().write(buffer.putLong(1L));
        serverReceivedMsg.await(100, MILLISECONDS);
    }
}
