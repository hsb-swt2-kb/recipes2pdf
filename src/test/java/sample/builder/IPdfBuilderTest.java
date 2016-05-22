package sample.builder;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;
import sample.builder.pdfBuilder.PdfBuilder;
import sample.builder.pdfBuilder.PdfBuilderConfig;
import sample.config.IConfig;
import sample.model.*;

import java.io.File;
import java.util.ArrayList;

public class IPdfBuilderTest {
    @Test

    public void testRecipePdfBuilder() throws Throwable {
        ArrayList<IConcreteBuilder> builderList = new ArrayList<>();
        IConfig config = IConfig.getInstance();
        config.setProperty("OUTPUT_FILETYPE","PDF");

        IConcreteBuilder pdfBuilder = new PdfBuilder(config);
        builderList.add(pdfBuilder);
        Builder builderController = new Builder(builderList);

        ICookbook cookbook = new Cookbook();
        cookbook.setTitle("DasKochbuch");

        for (int i = 1; i <= 10; i++) {
            IRecipe tempRecipe = new Recipe();
            ICategory category = new Category();

            tempRecipe.add("Salz",5,"g");
            category.setName("kategorie" + i);
            tempRecipe.setTitle(i + ". Rezept");
            tempRecipe.setCategory(category);
            tempRecipe.setText("Das ist ein Rezepttext");

            cookbook.addRecipe(tempRecipe);
        }

        File recipePDF = builderController.build(cookbook);
        PDDocument pdfDoc;

        pdfDoc = PDDocument.load(recipePDF);

        PDFTextStripper stripper = new PDFTextStripper("ISO-8859-1");
        stripper.setStartPage(3); //Start extracting from page 3
        stripper.setEndPage(4); //Extract till page 5
        System.out.println(stripper.getText(pdfDoc));

    }

}
