package com.akieus.lgc.tcp;

import java.nio.ByteBuffer;

/**
 * Created by aks on 19/03/2016.
 */
public interface MessageListener {
    void onMessage(ByteBuffer buffer);
}
