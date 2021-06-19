package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List) clone();
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return copy(this.list).get(index);
    }

    @Override
    protected synchronized Object clone() {
        return copy(this.list);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    private synchronized List<T> copy(List<T> list) {
        List<T> copyList;
        if (list == null) {
            copyList = new ArrayList<>();
        } else {
            copyList = new ArrayList<>(list);
        }
        return copyList;
    }
}