package com.akieus.lgc.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by aks on 19/03/2016.
 */
public class DefaultByteBufferProvider implements ByteBufferProvider {
    private final int capacity;
    private final boolean isDirect;
    private final ByteOrder byteOrder;

    public DefaultByteBufferProvider(int capacity, boolean isDirect) {
        this(capacity, isDirect, ByteOrder.nativeOrder());
    }

    public DefaultByteBufferProvider(int capacity, boolean isDirect, ByteOrder byteOrder) {
        this.capacity = capacity;
        this.isDirect = isDirect;
        this.byteOrder = byteOrder;
    }

    @Override
    public ByteBuffer get() {
        ByteBuffer buffer = isDirect ? ByteBuffer.allocateDirect(capacity) : ByteBuffer.allocate(capacity);
        buffer.order(byteOrder);
        return buffer;
    }
}
