package com.akieus.lgc.io;

import java.io.IOException;

/**
 * Created by aks on 19/03/2016.
 */
static class InconsistentBufferState extends IOException {
    public InconsistentBufferState() {
    }

    public InconsistentBufferState(Throwable cause) {
        super(cause);
    }
}
