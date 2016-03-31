package sample.pdfBuilder;


import org.junit.Test;

public class pdfBuilderTest {
    @Test
    public void testPdfBuider() {
        PdfBuilder builder = new PdfBuilder();
        builder.buildPDF();
    }
}
