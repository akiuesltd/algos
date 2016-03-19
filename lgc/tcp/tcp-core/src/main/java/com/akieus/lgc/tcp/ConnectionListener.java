package com.akieus.lgc.tcp;

/**
 * Created by aks on 19/03/2016.
 */
public interface ConnectionListener {
    void connected(TcpSession session);

    void disconnected(TcpSession session);
}
