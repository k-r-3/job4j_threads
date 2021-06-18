package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest {

    private static class StorageAdd extends Thread {
        private final UserStorage storage;

        private StorageAdd(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.add(new User(1, 100));
            storage.add(new User(2, 150));
            System.out.println(storage);
        }
    }

    @Test
    public void whenAdd() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new StorageAdd(storage);
        Thread second = new StorageAdd(storage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(2, is(storage.size()));
    }

    private static class StorageUpdate extends Thread {
        private final UserStorage storage;

        private StorageUpdate(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.add(new User(1, 100));
            storage.add(new User(2, 150));
            System.out.println(storage);
        }
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new StorageUpdate(storage);
        Thread second = new StorageUpdate(storage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.update(new User(1, 200)), is(true));
    }

    private static class StorageDelete extends Thread {
        private final UserStorage storage;

        private StorageDelete(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.add(new User(1, 100));
            storage.add(new User(2, 150));
            storage.delete(new User(1, 100));
            System.out.println(Thread.currentThread().getName());
            System.out.println(storage);
        }
    }

    @Test
    public void whenDelete() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new StorageDelete(storage);
        Thread second = new StorageDelete(storage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(1, is(storage.size()));
    }

    private static class StorageTrans extends Thread {
        private final UserStorage storage;

        private StorageTrans(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.add(new User(1, 100));
            storage.add(new User(2, 150));
            storage.transfer(2, 1, 100);
            System.out.println(Thread.currentThread().getName());
            System.out.println(storage);
        }
    }

    @Test
    public void whenTransfer() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new StorageTrans(storage);
        Thread second = new StorageTrans(storage);
        first.start();
        second.start();
        first.join();
        second.join();
    }
}