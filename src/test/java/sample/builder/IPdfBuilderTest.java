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

public class IPdfBuilderTest {
    @Test
    /**
     * This Method tests, if a PDF-File is beeing created by the Builder
     * Precondition: No PDF file exists
     * Nachbed: PDF File is saved on HDD. T
     */
    public void testCookbookPdfBuilder() throws Throwable {
        ArrayList<IConcreteBuilder> builderList = new ArrayList<>();
        IConfig config = IConfig.getInstance();


        IConcreteBuilder pdfBuilder = new PdfBuilder(config);
        builderList.add(pdfBuilder);
        Builder builderController = new Builder(builderList);

        ICookbook cookbook = new Cookbook();
        cookbook.setTitle("DasKochbuch");

        File recipePDF = builderController.build(cookbook);

        assertFalse(new File(config.getProperty("PROGRAM_USERDATA_DIR") + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + cookbook.getTitle() + ".pdf").exists());
    }

    public void testRecipePdfBuilder() throws Throwable {
        ArrayList<IConcreteBuilder> builderList = new ArrayList<>();
        IConfig config = IConfig.getInstance();


        IConcreteBuilder pdfBuilder = new PdfBuilder(config);
        builderList.add(pdfBuilder);
        Builder builderController = new Builder(builderList);

        IRecipe recipe = new Recipe();
        recipe.setTitle("DasRezept");

        File recipePDF = builderController.build(recipe);

        assertFalse(new File(config.getProperty("PROGRAM_USERDATA_DIR") + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + recipe.getTitle() + ".pdf").exists());
    }
}
