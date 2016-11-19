package com.akieus.lgc.io;

import java.io.IOException;

/**
 * Created by aks on 19/03/2016.
 */
public class InconsistentBufferState extends IOException {
    public InconsistentBufferState() {
    }

    public InconsistentBufferState(String message) {
        super(message);
    }

    public InconsistentBufferState(String message, Throwable cause) {
        super(message, cause);
    }

    public InconsistentBufferState(Throwable cause) {
        super(cause);
    }
}
