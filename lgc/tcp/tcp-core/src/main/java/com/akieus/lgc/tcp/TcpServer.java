package com.akieus.lgc.tcp;

import com.akieus.lgc.util.ByteBufferProvider;
import com.akieus.lgc.util.ConnectionOptions;
import com.akieus.lgc.util.Service;
import com.google.common.net.HostAndPort;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.akieus.lgc.util.Misc.namedThreadFactory;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 12/02/2016.
 */
public class TcpServer extends AbstractSpeaker<ConnectionListener> implements Service {
    private static final Logger LOG = getLogger(TcpServer.class);

    private final String name;
    private final HostAndPort hostAndPort;
    private final ConnectionOptions connectionOptions;
    private final MessageListener messageListener;
    private final ByteBufferProvider byteBufferProvider;

    private final ExecutorService acceptorThread;
    private final ExecutorService clientThreads;

    private volatile boolean stop;

    public TcpServer(String name, HostAndPort hostAndPort, ConnectionOptions connectionOptions, MessageListener messageListener, ByteBufferProvider byteBufferProvider) {
        this.name = name;
        this.hostAndPort = hostAndPort;
        this.connectionOptions = connectionOptions;
        this.messageListener = messageListener;
        this.byteBufferProvider = byteBufferProvider;

        this.acceptorThread = Executors.newSingleThreadExecutor(namedThreadFactory(name + "-Acceptor"));
        this.clientThreads = Executors.newCachedThreadPool(namedThreadFactory(name + "-%d"));
    }

    @Override
    public void start() {
        acceptorThread.submit(this::startListening);
    }

    private void startListening() {
        LOG.warn("Server {} started listening on {}", name, hostAndPort);
        ServerSocketChannel ssc;
        try {
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(InetAddress.getByName(hostAndPort.getHostText()), hostAndPort.getPort()));
            if (connectionOptions.getReceiveBufferSize() != 0) {
                ssc.setOption(StandardSocketOptions.SO_RCVBUF, connectionOptions.getReceiveBufferSize());
            }
        } catch (IOException e) {
            if (!stop) LOG.error("Could not start server {} on hostPort={}", name, hostAndPort);
            return;
        }
        while (!stop) {
            try {
                accept(ssc);
            } catch (IOException e) {
                if (!stop) LOG.error("Error while listening to client connections", e);
            }
        }
        LOG.warn("Server stopped listening on {}", hostAndPort);
    }

    private void accept(ServerSocketChannel ssc) throws IOException {
        SocketChannel channel = ssc.accept();
        setupClient(channel);
    }

    private void setupClient(SocketChannel channel) {
        TcpSession session = new TcpSession(channel, byteBufferProvider, messageListener);
        notifyListeners(connectionListener -> connectionListener.connected(session));
        clientThreads.submit(() -> read(session));
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
        acceptorThread.shutdownNow();
        clientThreads.shutdownNow();
    }
}
