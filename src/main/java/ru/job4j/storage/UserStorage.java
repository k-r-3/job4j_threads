package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public synchronized boolean add(User user) {
        User added = new User(user.getId(), user.getAmount());
        if (!map.containsValue(added)) {
            map.put(id.incrementAndGet(), new User(user.getId(), user.getAmount()));
            return true;
        }
        return false;
    }

    public synchronized boolean update(User user) {
        int userId = getKey(user);
        return action(userId, i -> map.put(userId, user));
    }

    public synchronized boolean delete(User user) {
        int userId = getKey(user);
        return action(userId, i -> map.remove(userId));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = new User(map.get(fromId).getId(), map.get(fromId).getAmount());
        User to = new User(map.get(toId).getId(), map.get(toId).getAmount());
        int difference = from.getAmount() - amount;
        if (difference >= 0) {
            update(new User(fromId, difference));
            return update(new User(toId, to.getAmount() + amount));
        }
        return false;
    }

    private synchronized boolean action(int userId, Consumer<Integer> rsl) {
       if (userId != -1) {
           rsl.accept(userId);
           return true;
       }
       return false;
    }

    private synchronized int getKey(User user) {
        int id = -1;
        for (int i = 1; i <= map.size(); i++) {
            Optional<User> interUser = Optional.ofNullable(map.get(i));
            if (interUser.isPresent()) {
                if (interUser.get().getId() == user.getId()) {
                    id = i;
                    return id;
                }
            }
        }
        return id;
    }

    public synchronized int size() {
        return map.size();
    }

    public synchronized String toString() {
        return map.toString();
    }
}
