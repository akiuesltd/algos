package com.akieus.lgc.tcp;

import com.akieus.lgc.tcp.util.TestUtils;
import com.akieus.lgc.util.ByteBufferProvider;
import com.akieus.lgc.util.ConnectionOptions;
import com.akieus.lgc.util.DefaultByteBufferProvider;
import com.akieus.lgc.util.Misc;
import com.akieus.lgc.util.metrics.LongMinMaxAverage;
import com.google.common.net.HostAndPort;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Not a valid perf tess, created a POC.
 * <p>
 * Created by aks on 19/03/2016.
 */
public class TcpClientServerPerfITest {
    private static final Logger LOG = getLogger(TcpClientServerPerfITest.class);

    private static final String TEST_SERVER = "TEST_SERVER";
    private static final String TEST_CLIENT = "TEST_CLIENT";

    @Test
    public void testLatency() throws InterruptedException, IOException {
        int runs = 1_000_000;
        int warmup = 1000;
        CountDownLatch serverReceivedAllMessages = new CountDownLatch(runs);
        LongMinMaxAverage metrics = new LongMinMaxAverage(warmup);
        ServerListener serverListener = new ServerListener(serverReceivedAllMessages, metrics);
        ByteBufferProvider bufferProvider = new DefaultByteBufferProvider(1024, false);

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
        Assert.assertTrue(clientConnected.await(100, MILLISECONDS));

        ByteBuffer buffer = bufferProvider.get();
        for (int i = 0; i < runs; i++) {
            buffer.clear();
            clientSession.get().write(buffer.putLong(System.nanoTime()));
        }

        Assert.assertTrue(serverReceivedAllMessages.await(10, SECONDS));
        LOG.info("count=" + metrics.count());
        LOG.info("measures=" + metrics.measures());
        LOG.info("max=" + metrics.max());
        LOG.info("min=" + metrics.min());
        LOG.info("avg=" + metrics.avg());
    }

    private static class ServerListener implements MessageListener {
        private final LongMinMaxAverage metrics;
        private final CountDownLatch serverReceivedAllMessages;

        public ServerListener(CountDownLatch serverReceivedAllMessages, LongMinMaxAverage metrics) {
            this.serverReceivedAllMessages = serverReceivedAllMessages;
            this.metrics = metrics;
        }

        @Override
        public void onMessage(ByteBuffer buffer) {
            LOG.trace("onMessage, buffer={}", buffer.toString());
            buffer.flip();
            while (buffer.remaining() >= Misc.LONG_SIZE) {
                long latency = System.nanoTime() - buffer.getLong();
                metrics.add(latency);
                serverReceivedAllMessages.countDown();
            }
            buffer.compact();
            LOG.trace("returning, buffer={}", buffer.toString());
        }
    }

}
