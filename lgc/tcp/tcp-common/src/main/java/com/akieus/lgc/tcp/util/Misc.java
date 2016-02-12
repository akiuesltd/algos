package com.akieus.lgc.tcp.util;

import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 12/02/2016.
 */
public class Misc {
    private static final Logger LOG = getLogger(Misc.class);

    public static void sleepSafely(final long duration, final TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T> T ifNullDefault(final T value, final T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static void main(String[] args) {
        LOG.info("Starting...");
        sleepSafely(1, TimeUnit.SECONDS);
        LOG.info("Started...");
    }
}
