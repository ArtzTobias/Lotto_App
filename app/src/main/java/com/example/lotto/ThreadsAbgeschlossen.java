package com.example.lotto;

public class ThreadsAbgeschlossen {

    private boolean thread1 = false;
    private boolean thread2 = false;

    public ThreadsAbgeschlossen(boolean thread1) {
        this.thread1 = thread1;
    }

    public void setThread2(boolean thread2) {
        this.thread2 = thread2;
    }

    public boolean isFirstThreadFinished() {
        return thread1;
    }
    public boolean isSecondThreadFinished() {
        return thread2;
    }
}
