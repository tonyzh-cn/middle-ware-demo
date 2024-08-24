package com.example.redis;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedissionTest {

    @Resource
    private Redisson redisson;

    @Test
    public void testRedisson() {
        RLock lock = redisson.getLock("redis-demo:test");
        try {
            lock.lock();
        }finally {
            lock.unlock();
        }
    }
}
