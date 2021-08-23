package ru.task.printmanager.printers;

import org.junit.Test;
import ru.task.printmanager.PrintDispatcherSample;
import ru.task.printmanager.documents.Document;

import static org.junit.Assert.assertEquals;
import static ru.task.printmanager.documents.constants.DocumentType.TEXT;

public class ConsolePrinterTest {
    private Long printDuration;

    @Test
    public void print() throws InterruptedException {
        Document document = PrintDispatcherSample.createRandomDocument(TEXT);
        document.print(new PrintTestable());

        assertEquals(document.getPrintDuration(), printDuration);
    }

    private class PrintTestable extends ConsolePrinter {
        @Override
        public void print(String info, Long duration) throws InterruptedException {
            printDuration = duration;
            super.print(info, duration);
        }
    }
}