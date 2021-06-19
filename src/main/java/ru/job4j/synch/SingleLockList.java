package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>, Cloneable {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List) clone();
    }

    public synchronized void add(T value) {
    }

    public synchronized T get(int index) {
        return null;
    }

    @Override
    protected synchronized Object clone() {
        List<T> rsl = new ArrayList<>();
        Collections.copy(rsl, list);
        return rsl;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    private List<T> copy(List<T> list) {
        List<T> copyList = new ArrayList<>();
        copyList.addAll(list);
        return copyList;
    }
}