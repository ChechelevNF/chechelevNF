package ru.task.printmanager.manager;

import ru.task.printmanager.documents.Document;
import ru.task.printmanager.printers.ConsolePrinter;
import ru.task.printmanager.printers.Printer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PrintManager {
    private PrintQueue printQueue = new PrintQueue();
    private Printer printer = new ConsolePrinter();
    private volatile Boolean canceled = false;

    public Boolean isCanceled() {
        return canceled;
    }

    public void putDocument(Document document) {
        if (!canceled) {
            printQueue.putDocumentToQueue(document);
        }
    }

    public void printDocument() throws InterruptedException {
        if (!canceled) {
            printQueue.printDocument(printer);
        }
    }

    public void cancelDocument() {
        printQueue.cancelPrinting();
    }

    public List<Document> cancelPrinting() {
        printQueue.cancelPrinting();
        printQueue.reset();
        canceled = true;

        return new ArrayList<>(printQueue.getWrapped());
    }

    public List<Document> getPrinted(@Nullable Comparator<Document> comparator) {
        if (comparator != null) {
            return printQueue.getPrinted(comparator);
        }
        return printQueue.getPrinted();
    }

    public Long getAveragePrintingDuration() {
        return printQueue.getAveragePrintingDuration();
    }

}
