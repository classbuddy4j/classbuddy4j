package io.github.classbuddy4j.faketime;

public class FakeClock {
    private volatile long millis = 0;
    public FakeClock() {
    }

    public FakeClock(final long millis) {
       this.millis = millis;
    }

    public long currentTimeMillis() {
        return this.millis;
    }

    public void setCurrentTimeMillis(long millis) {
        this.millis = millis;
    }

    @Override
    public String toString() {
        return "millis=" + currentTimeMillis();
    }
}