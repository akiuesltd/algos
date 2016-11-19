package com.akieus.lgc.udp;

import com.akieus.lgc.AbstractSpeaker;
import com.akieus.lgc.Service;
import com.akieus.lgc.io.ByteBufferProvider;
import com.akieus.lgc.io.InconsistentBufferState;
import com.akieus.lgc.io.MessageListener;
import com.akieus.lgc.util.LangUtil;
import com.google.common.net.HostAndPort;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;

import static com.akieus.lgc.util.Misc.namedThreadFactory;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
public class UdpSource extends AbstractSpeaker<ConnectionListener> implements Service {
    private static final Logger LOG = getLogger(UdpSource.class);

    private final String name;
    private final HostAndPort address;

    private final MessageListener messageListener;
    private final ByteBufferProvider byteBufferProvider;

    private final ExecutorService readerThread;
    private volatile boolean stop;

    public UdpSource(String name, HostAndPort address, MessageListener messageListener, ByteBufferProvider byteBufferProvider) {
        this.name = name;
        this.address = address;
        this.messageListener = messageListener;
        this.byteBufferProvider = byteBufferProvider;

        this.readerThread = newSingleThreadExecutor(namedThreadFactory("UdpSource-" + name));
    }

    @Override
    public void start() {
        DatagramChannel dc;
        try {
            dc = DatagramChannel.open();
            dc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            dc.bind(new InetSocketAddress(InetAddress.getByName(address.getHostText()), address.getPort()));
        } catch (IOException e) {
            LOG.warn("Could not start UdpSource on address={}", address);
            LangUtil.rethrowUnchecked(e);
            return; // unreachable
        }

        UdpSession session = new UdpSession(dc, byteBufferProvider, messageListener);
        notifyListeners((connectionListener) -> connectionListener.connected(session));
        readerThread.submit(() -> read(session));
    }

    private void read(UdpSession session) {
        while (!stop) {
            try {
                session.read();
            } catch (InconsistentBufferState e) {
                LOG.warn("Inconsistent buffer state, reset the connection", e);
                session.close();
            } catch (ClosedChannelException e) {
                break;
            } catch (IOException e) {
                LOG.warn("Unknown IOException, reset the connection", e);
                session.close();
            }
        }
    }


    @Override
    public void stop() {
        stop = true;

    }
}
