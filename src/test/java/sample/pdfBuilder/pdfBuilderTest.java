package sample.pdfBuilder;


import org.junit.Test;
import sample.model.ICookbook;

import java.io.File;

public class pdfBuilderTest {
    @Test
    public void testPdfBuider() {
        PdfBuilder builder = new PdfBuilder();
        // List<File> pdfFiles = builder.buildPDF(ICookbook.getInstance());
        File cookbookPDF = builder.createPDF(ICookbook.getInstance());

    }
}
