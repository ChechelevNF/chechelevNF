package ru.task.printmanager.manager;

import org.junit.Before;
import org.junit.Test;
import ru.task.printmanager.PrintDispatcherSample;
import ru.task.printmanager.printers.ConsolePrinter;
import ru.task.printmanager.printers.Printer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static ru.task.printmanager.documents.constants.DocumentType.*;

public class PrintQueueTest {
    PrintQueue queue;

    @Before
    public void setUp() {
        queue = new PrintQueue();
        queue.putDocumentToQueue(PrintDispatcherSample.createRandomDocument(TEXT));
    }

    @Test
    public void getDocumentWhenExists() throws InterruptedException {
        assertNotNull(queue.getDocument());
    }

    @Test
    public void putDocumentToQueue() {
        int size = queue.wrapped.size();
        queue.putDocumentToQueue(PrintDispatcherSample.createRandomDocument(TEXT));
        assertEquals(size + 1, queue.wrapped.size());
    }

    @Test
    public void printDocument() throws InterruptedException {
        int size = queue.wrapped.size();
        waitWhilePrinted();

        assertEquals(size - 1, queue.wrapped.size());
    }

    private void waitWhilePrinted() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Printer printer = new ConsolePrinter();
        queue.printDocument(printer);
        queue.getDocument();
    }

    @Test
    public void getPrinted() throws InterruptedException {
        waitWhilePrinted();

        assertEquals(1, queue.getPrinted().size());
        assertEquals(queue.getAveragePrintingDuration(), queue.getPrinted().get(0).getPrintDuration());

    }
}