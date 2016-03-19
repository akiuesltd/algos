package com.akieus.lgc.tcp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractSpeakerTest {
    private static final Logger LOG = getLogger(AbstractSpeakerTest.class);

    @Mock
    private TcpSession tcpSession;

    @Test
    public void allRegisteredListenersAreInvoked() throws InterruptedException {
        Speaker<ConnectionListener> speaker = new AbstractSpeaker<ConnectionListener>() {
        };
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        speaker.addListener(getConnectionListener(1, latch1));
        speaker.addListener(getConnectionListener(2, latch2));
        speaker.notifyListeners(listener -> listener.connected(tcpSession));
        latch1.await();
        latch2.await();
    }

    private ConnectionListener getConnectionListener(final int id, final CountDownLatch latch) {
        return new ConnectionListener() {
            @Override
            public void connected(TcpSession session) {
                LOG.info("connected {}", id);
                latch.countDown();
            }

            @Override
            public void disconnected(TcpSession session) {
                LOG.info("disconnected {}", id);
            }
        };
    }
}
