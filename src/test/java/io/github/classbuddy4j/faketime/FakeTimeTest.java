package io.github.classbuddy4j.faketime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FakeTimeTest {
    @Test
    void fakeTimeIsInstalled() throws Exception {
        final long initMillis = System.currentTimeMillis() - 2500;
        FakeClock clock = new FakeClock();
        clock.setCurrentTimeMillis(initMillis);
        Installer.installFakeTime(clock);
        assertEquals(initMillis, System.currentTimeMillis());
        Thread.sleep(500);
        assertEquals(initMillis, System.currentTimeMillis());
    }
}
