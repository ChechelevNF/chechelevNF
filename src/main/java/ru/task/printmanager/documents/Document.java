package ru.task.printmanager.documents;

import ru.task.printmanager.documents.constants.PageSize;
import ru.task.printmanager.printers.Printer;

public interface Document {

    String getDocumentType();

    Long getPrintDuration();

    PageSize getPageSize();

    String getDocumentName();

    void print(Printer printer) throws InterruptedException;
}