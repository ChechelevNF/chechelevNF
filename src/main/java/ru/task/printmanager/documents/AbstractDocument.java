package ru.task.printmanager.documents;

import ru.task.printmanager.printers.Printer;

import java.text.MessageFormat;

public abstract class AbstractDocument implements Document {

    public void print(Printer printer) throws InterruptedException {
        printer.print(
                MessageFormat.format("Name: {0}\nType: {1}\nPageSize: {2}\nPrint duration: {3}",
                        this.getDocumentName(),
                        this.getDocumentType(),
                        this.getPageSize(),
                        this.getPrintDuration()
                ),
                this.getPrintDuration());
    }
}
