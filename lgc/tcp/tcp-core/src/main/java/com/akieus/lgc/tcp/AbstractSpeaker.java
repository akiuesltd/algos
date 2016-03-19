package com.akieus.lgc.tcp;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Created by aks on 19/03/2016.
 */
public abstract class AbstractSpeaker<T> implements Speaker<T> {

    protected List<T> listeners = new CopyOnWriteArrayList<>();

    @Override
    public void addListener(T listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(Consumer<T> fn) {
        listeners.forEach(fn::accept);
    }
}
