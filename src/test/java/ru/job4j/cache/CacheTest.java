package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddThenTrue() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        assertThat(cache.add(first), is(true));
    }

    @Test
    public void whenAddThenFalse() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        assertThat(cache.add(first), is(false));
    }

    @Test
    public void whenRemoveThenTrue() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        assertThat(cache.delete(first), is(true));
    }

    @Test
    public void whenRemoveThenFalse() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        cache.delete(first);
        assertThat(cache.delete(first), is(false));
    }

    @Test
    public void whenUpdateOnce() {
        Cache cache = new Cache();
        Base first = new Base(0, 0);
        cache.add(first);
        cache.get(0).setName("element");
        cache.update(cache.get(0));
        assertThat(cache.get(first.getId()).getVersion(), is(1));
    }

    @Test
    public void whenUpdateTwice() {
        Cache cache = new Cache();
        Base first = new Base(0, 0);
        cache.add(first);
        cache.get(0).setName("element");
        cache.update(cache.get(0));
        cache.get(0).setName("element 2");
        cache.update(cache.get(0));
        assertThat(cache.get(first.getId()).getVersion(), is(2));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateThenException() {
        Cache cache = new Cache();
        Base first = new Base(0, 0);
        cache.add(first);
        cache.get(0).setName("element");
        cache.update(cache.get(0));
        first.setName("element");
        cache.update(first);
        cache.update(cache.get(0));
    }
}