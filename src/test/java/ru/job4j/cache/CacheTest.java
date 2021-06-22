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
    public void whenUpdate() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        cache.update(first);
        assertThat(cache.get(first.getId()).getVersion(), is(1));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateException() {
        Cache cache = new Cache();
        Base first = new Base(1, 0);
        cache.add(first);
        cache.update(first);
        cache.update(first);
    }

}