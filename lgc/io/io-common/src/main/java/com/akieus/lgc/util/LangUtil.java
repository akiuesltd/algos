package com.akieus.lgc.util;

/**
 * Created by aks on 19/03/2016.
 * Original source code at: https://github.com/real-logic/Agrona/blob/master/src/main/java/uk/co/real_logic/agrona/LangUtil.java
 */
public class LangUtil {
    public static void rethrowUnchecked(final Exception ex) {
        LangUtil.<RuntimeException>rethrow(ex);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Exception> void rethrow(final Exception ex) throws T {
        throw (T) ex;
    }

}
