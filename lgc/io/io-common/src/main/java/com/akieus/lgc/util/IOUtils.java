package com.akieus.lgc.util;

import com.akieus.lgc.util.LangUtil;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by aks on 19/03/2016.
 */
public class IOUtils {
    public static int findFreePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            LangUtil.rethrowUnchecked(e);
        }
        return 0; //unreachable
    }

    public static void main(String[] args) {
        System.out.println(findFreePort());
        System.out.println(findFreePort());
        System.out.println(findFreePort());
    }
}
