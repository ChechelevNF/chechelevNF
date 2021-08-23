package ru.task.printmanager;

import ru.task.printmanager.manager.PrintManager;
import ru.task.printmanager.documents.*;
import ru.task.printmanager.documents.constants.PageSize;
import ru.task.printmanager.printers.ConsolePrinter;
import ru.task.printmanager.printers.Printer;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.task.printmanager.documents.constants.DocumentType.*;

public class PrintDispatcherSample {

    public static void main(String[] args) throws InterruptedException {
        final PrintManager printManager = new PrintManager();
        final Printer printer = new ConsolePrinter();

        ExecutorService executor = Executors.newCachedThreadPool();
        // в отдельном потоке формируем документы для очереди
        executor.execute(() -> {
            while (!printManager.isCanceled()) {
                printManager.putDocument(createRandomDocument(TEXT));
                printManager.putDocument(createRandomDocument(DRAWING));
                printManager.putDocument(createRandomDocument(PICTURE));
            }
        });

        executor.execute(() -> {
            while (!printManager.isCanceled()) {
                try {
                    printManager.printDocument();
                } catch (InterruptedException e) {
                }
            }
        });

        Thread.sleep(10000);

        List<Document> printedByName = printManager.getPrinted(Comparator.comparing(Document::getDocumentName));
        Long averagePrintingDuration = printManager.getAveragePrintingDuration();

        System.out.println("Печать прервана");
        printer.print("Не напечатанные документы:", 0L);
        for (Document document : printManager.cancelPrinting()) {
            document.print(printer);
        }


        printer.print("Документы, отсортированные по имени", 0L);
        for (Document document : printedByName) {
            document.print(printer);
        }

        printer.print("Среднее время обработки " + averagePrintingDuration, 0L);
        printer.print("Завершение работы", 0L);
    }

    @Nullable
    public static Document createRandomDocument(String documentType) {
        Random random = new Random();
        int number = random.nextInt(100);
        int duration = random.nextInt(5000);
        int pageSize = random.nextInt(3);
        switch (documentType) {
            case (TEXT):
                return new TextDocument((long) duration, PageSize.getByOrdinal(pageSize),
                        documentType + " №" + number);
            case (DRAWING):
                return new TableDocument((long) duration, PageSize.getByOrdinal(pageSize),
                        documentType + " №" + number);
            case (PICTURE):
                return new DrawingDocument((long) duration, PageSize.getByOrdinal(pageSize),
                        documentType + " №" + number);
        }
        return null;
    }
}
