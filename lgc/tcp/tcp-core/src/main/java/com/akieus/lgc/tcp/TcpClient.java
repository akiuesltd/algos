package com.akieus.lgc.tcp;

import com.akieus.lgc.util.ByteBufferProvider;
import com.akieus.lgc.util.ConnectionOptions;
import com.akieus.lgc.util.Service;
import com.google.common.net.HostAndPort;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.akieus.lgc.util.Misc.namedThreadFactory;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 12/02/2016.
 */
public class TcpClient extends AbstractSpeaker<ConnectionListener> implements Service {
    private static final Logger LOG = getLogger(TcpClient.class);

    private final String name;
    private final HostAndPort hostAndPort;
    private final ConnectionOptions connectionOptions;
    private final MessageListener messageListener;
    private final ByteBufferProvider byteBufferProvider;

    private final ExecutorService connectionThread;

    private volatile boolean stop;

    public TcpClient(String name, HostAndPort hostAndPort, ConnectionOptions connectionOptions, MessageListener messageListener, ByteBufferProvider byteBufferProvider) {
        this.name = name;
        this.hostAndPort = hostAndPort;
        this.connectionOptions = connectionOptions;
        this.messageListener = messageListener;
        this.byteBufferProvider = byteBufferProvider;

        this.connectionThread = Executors.newSingleThreadExecutor(namedThreadFactory("TcpClient-" + name));
    }

    @Override
    public void start() {
        connectionThread.submit(this::connect);
    }

    private void connect() {
        LOG.info("Client {} trying to connect to {}", name, hostAndPort);
        try {
            SocketChannel channel = SocketChannel.open();
            if (channel.connect(new InetSocketAddress(InetAddress.getByName(hostAndPort.getHostText()), hostAndPort.getPort()))) {
                LOG.info("Client {} connected to {}", name, hostAndPort);
                connectionOptions.configure(channel);
                TcpSession session = new TcpSession(channel, byteBufferProvider, messageListener);
                notifyListeners(connectionListener -> connectionListener.connected(session));
                read(session);
            }
        } catch (IOException e) {
            if (!stop) LOG.error("Could not start server {} on hostPort={}", name, hostAndPort);
            return;
        }
        LOG.warn("Client {} - thread exiting connection to {}", name, hostAndPort);
    }

    private void read(TcpSession session) {
        while (!stop) {
            try {
                session.read();
            } catch (TcpSession.InconsistentBufferState e) {
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
        connectionThread.shutdownNow();
    }
}
