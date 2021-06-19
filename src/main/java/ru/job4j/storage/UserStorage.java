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
        User current = map.putIfAbsent(user.getId(), user);
        if (current == null) {
            return true;
        } else {
            map.put(current.getId(), current);
            return false;
        }
    }

    public synchronized boolean update(User user) {
        User current = map.replace(user.getId(), user);
        if (current != null) {
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return map.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = map.get(fromId);
        User to = map.get(toId);
        if (from != null && to != null) {
            int difference = from.getAmount() - amount;
            if (difference >= 0) {
                update(new User(fromId, difference));
                return update(new User(toId, to.getAmount() + amount));
            }
        }
        return false;
    }
}
