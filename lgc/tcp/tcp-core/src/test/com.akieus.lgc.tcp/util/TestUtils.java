package com.akieus.lgc.tcp.util;

import com.akieus.lgc.util.LangUtil;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by aks on 19/03/2016.
 */
public class TestUtils {
    public static int findFreePort() {
        try {
            return new ServerSocket(0).getLocalPort();
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
