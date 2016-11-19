package com.akieus.lgc.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.DatagramChannel;

/**
 * Created by aks on 19/03/2016.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        dc.bind(new InetSocketAddress(InetAddress.getByName(args[0]), Integer.parseInt(args[1])));
        System.out.println("Phew...");
    }
}
