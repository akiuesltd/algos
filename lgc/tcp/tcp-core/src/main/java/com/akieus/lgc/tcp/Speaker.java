package com.akieus.lgc.tcp;

import java.util.function.Consumer;

/**
 * Created by aks on 19/03/2016.
 */
public interface Speaker<T> {
    void addListener(T listener);

    void removeListener(T listener);

    void notifyListeners(Consumer<T> fn);
}
