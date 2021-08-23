package ru.task.printmanager.documents;

import org.junit.Test;
import ru.task.printmanager.documents.constants.PageSize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PageSizeTest {

    @Test
    public void getByOrdinal() {
        PageSize pageSize = PageSize.getByOrdinal(0);
        assertEquals(PageSize.A1, pageSize);
        pageSize = PageSize.getByOrdinal(1);
        assertEquals(PageSize.A4, pageSize);
        pageSize = PageSize.getByOrdinal(2);
        assertEquals(PageSize.A5, pageSize);
    }

    @Test
    public void wrongOrdinal() {
        PageSize pageSize = PageSize.getByOrdinal(-1);
        assertNull(pageSize);
    }
}