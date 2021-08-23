package ru.task.printmanager.documents;

import ru.task.printmanager.documents.constants.PageSize;

import static ru.task.printmanager.documents.constants.DocumentType.PICTURE;

public class DrawingDocument extends AbstractDocument {
    private Long printDuration;
    private PageSize pageSize;
    private String documentName;

    public DrawingDocument(Long printDuration, PageSize pageSize, String documentName) {
        this.printDuration = printDuration;
        this.pageSize = pageSize;
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return PICTURE;
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
