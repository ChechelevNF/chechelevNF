package ru.task.printmanager.documents;

import ru.task.printmanager.documents.constants.PageSize;

import static ru.task.printmanager.documents.constants.DocumentType.DRAWING;

public class TableDocument extends AbstractDocument {
    private Long printDuration;
    private PageSize pageSize;
    private String documentName;

    public TableDocument(Long printDuration, PageSize pageSize, String documentName) {
        this.printDuration = printDuration;
        this.pageSize = pageSize;
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return DRAWING;
    }

    public Long getPrintDuration() {
        return printDuration;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public String getDocumentName() {
        return documentName;
    }
}
