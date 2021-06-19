package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> map = new HashMap<>();

    public synchronized boolean add(User user) {
        map.putIfAbsent(user.getId(), user);
        return true;
    }

    public synchronized boolean update(User user) {
        map.replace(user.getId(), user);
        return true;
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = map.get(fromId);
        if (from.getAmount() > 0) {
            User to = map.get(toId);
            int difference = from.getAmount() - amount;
            if (difference >= 0) {
                update(new User(fromId, difference));
                return update(new User(toId, to.getAmount() + amount));
            }
        }
        return false;
    }
}
