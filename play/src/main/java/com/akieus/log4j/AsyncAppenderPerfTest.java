package com.akieus.log4j;

import org.apache.log4j.Logger;

public class AsyncAppenderPerfTest {

    public static Logger LOGGER_RESULTS = Logger.getLogger("LOGGER_RESULTS");

    public static Logger LOGGER_SYNC_NULL = Logger.getLogger("LOGGER_NULL");
    public static Logger LOGGER_SYNC_FILE = Logger.getLogger("LOGGER_FILE");
    public static Logger LOGGER_SYNC_CONSOLE = Logger.getLogger("LOGGER_CONSOLE");
    public static Logger LOGGER_ASYNC_APPENDER_NONE = Logger.getLogger("LOGGER_ASYNC_NONE");
    public static Logger LOGGER_ASYNC_APPENDER_NULL = Logger.getLogger("LOGGER_ASYNC_NULL");
    public static Logger LOGGER_ASYNC_APPENDER_CONSOLE = Logger.getLogger("LOGGER_ASYNC_CONSOLE");
    public static Logger LOGGER_ASYNC_APPENDER_CONSOLE_FILE = Logger.getLogger("LOGGER_ASYNC_CONSOLE_FILE");
    public static Logger LOGGER_ASYNC_APPENDER_CONSOLE_FILE_NULL = Logger.getLogger("LOGGER_ASYNC_CONSOLE_FILE_NULL");
    public static Logger LOGGER_ASYNC_APPENDER_FILE = Logger.getLogger("LOGGER_ASYNC_FILE");
    public static Logger LOGGER_ASYNC_APPENDER_FILE_NULL = Logger.getLogger("LOGGER_ASYNC_FILE_NULL");

    public static void main(String[] args) throws Exception {
        int repeat = 3;
        for (int i = 0; i < repeat; i++) {
            if (i != 0) {
                LOGGER_RESULTS.info("-------------------------------------------------------");
            }

            // SYNC
            test(LOGGER_SYNC_NULL);
            test(LOGGER_SYNC_FILE);
            test(LOGGER_SYNC_CONSOLE);

            // ASYNC
            test(LOGGER_ASYNC_APPENDER_NONE);
            test(LOGGER_ASYNC_APPENDER_NULL);

            test(LOGGER_ASYNC_APPENDER_FILE);
            test(LOGGER_ASYNC_APPENDER_FILE_NULL);

            test(LOGGER_ASYNC_APPENDER_CONSOLE);
            test(LOGGER_ASYNC_APPENDER_CONSOLE_FILE);
            test(LOGGER_ASYNC_APPENDER_CONSOLE_FILE_NULL);
        }
    }

    private static void test(Logger logger) throws Exception {
        int n = 1000 * 1000;
        long t1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            logger.info("test");
        }

        long t2 = System.nanoTime();

        // let stuff flush.
        Thread.sleep(5000);

        // print results
        LOGGER_RESULTS.info(logger.getName() + ": time per request: " + (t2 - t1) / n + "ns");
    }

}
