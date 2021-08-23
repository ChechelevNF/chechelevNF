package ru.task.printmanager.manager;

import ru.task.printmanager.documents.Document;
import ru.task.printmanager.printers.Printer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class PrintQueue {
    Queue<Document> wrapped = new LinkedList<>();
    private List<Document> printedDocuments = new ArrayList<>();
    private Boolean printing = false;
    private AtomicLong totalPrintingDuration = new AtomicLong();

    {
        totalPrintingDuration.set(0);
    }

    private ExecutorService printingExecutor = Executors.newCachedThreadPool();
    private final Object printingLock = new Object();

    Document getDocument() throws InterruptedException {
        synchronized (printingLock) {
            while (printing) {
                printingLock.wait();
            }
        }
        return wrapped.size() > 0 ? wrapped.element() : null;
    }

    synchronized void putDocumentToQueue(Document document) {
        wrapped.add(document);
    }

    void printDocument(final Printer printer) throws InterruptedException {
        final Document document = getDocument();
        if (document == null) {
            printing = false;
            return;
        }

        //Принтер работает в отдельном потоке
        printing = true;
        printingExecutor.execute(() -> {
            synchronized (printingLock) {
                try {
                    document.print(printer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    totalPrintingDuration.addAndGet(document.getPrintDuration());
                    printedDocuments.add(wrapped.poll());
                    printing = false;
                    printingLock.notifyAll();
                }
            }
        });
    }

    synchronized void cancelPrinting() {
        if (!printing) {
            return;
        }
        printing = false;

        notifyAll();
    }

    List<Document> getPrinted() {
        return printedDocuments;
    }

    public List<Document> getPrinted(Comparator<Document> comparator) {
        printedDocuments.sort(comparator);
        return getPrinted();
    }

    Long getAveragePrintingDuration() {
        if (printedDocuments.isEmpty()) {
            return 0L;
        }
        return totalPrintingDuration.get() / printedDocuments.size();
    }

    void reset() {
        cancelPrinting();
        printedDocuments = new ArrayList<>();
        wrapped = new LinkedList<>();
        totalPrintingDuration.set(0L);
    }

    public Queue<Document> getWrapped() {
        return wrapped;
    }
}
