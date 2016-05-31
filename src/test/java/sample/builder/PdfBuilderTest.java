package sample.builder;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;
import sample.builder.pdfBuilder.PdfBuilder;
import sample.builder.pdfBuilder.PdfBuilderConfig;
import sample.config.IConfig;
import sample.model.*;
import static org.javalite.test.jspec.JSpec.the;
import java.io.File;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PdfBuilderTest {
    @Test
    /**
     * This Method tests, if a PDF-File is beeing created by the Builder
     * Precondition: No PDF file exists
     * Nachbed: PDF File is saved on HDD. T
     */
    public void testCookbookPdfBuilder() throws Throwable {
        IConfig config = IConfig.getInstance();
        IConcreteBuilder pdfBuilder = new PdfBuilder(config);

        ICookbook cookbook = new Cookbook();
        cookbook.setTitle("DasKochbuch");

        assertFalse(new File(config.getProperty("PROGRAM_USERDATA_DIR") + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + cookbook.getTitle() + ".pdf").exists());
    }

    public void testRecipePdfBuilder() throws Throwable {
        IConfig config = IConfig.getInstance();

        IConcreteBuilder pdfBuilder = new PdfBuilder(config);

        IRecipe recipe = new Recipe();
        recipe.setTitle("DasRezept");

        File recipePDF = pdfBuilder.build(recipe);

        assertFalse(new File(config.getProperty("PROGRAM_USERDATA_DIR") + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + recipe.getTitle() + ".pdf").exists());
    }
}
