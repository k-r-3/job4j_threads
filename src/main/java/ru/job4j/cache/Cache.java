package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (i, b) -> {
                    Base stored = memory.get(model.getId());
                    if (stored.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    Base updated = new Base(i, b.getVersion() + 1);
                    updated.setName(stored.getName());
                    return updated;
                }
                ) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }

    public Base get(int id) {
        return memory.get(id);
    }
}