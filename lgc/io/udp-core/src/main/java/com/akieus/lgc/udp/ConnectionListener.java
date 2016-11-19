package com.akieus.lgc.udp;

/**
 * Created by aks on 19/03/2016.
 */
public interface ConnectionListener {
    void connected(UdpSession session);

    void disconnected(UdpSession session);
}
