package ru.task.printmanager.printers;

public interface Printer {
    void print(String info, Long duration) throws InterruptedException;
}
