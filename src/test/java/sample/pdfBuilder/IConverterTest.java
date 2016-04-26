package sample.pdfBuilder;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;
import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;

public class IConverterTest {
    @Test

    public void testRecipePdfBuilder() throws Throwable {
        Converter builder = new Converter(new ConverterConfig());


        IRecipe recipe = IRecipe.getInstance();
        recipe.setTitle("Mein Rezept");

        File recipePDF = builder.buildPDF(recipe);

        PDDocument pdfDoc;

        pdfDoc = PDDocument.load(recipePDF);

        PDFTextStripper stripper = new PDFTextStripper("ISO-8859-1");
        stripper.setStartPage(3); //Start extracting from page 3
        stripper.setEndPage(4); //Extract till page 5
        System.out.println(stripper.getText(pdfDoc));

    }


    public void testCookbookPdfBuilder() {
        Converter builder = new Converter(new ConverterConfig());

        ICookbook cookbook = ICookbook.getInstance();

        for (int i = 1; i <= 10; i++) {
            IRecipe tempRecipe = IRecipe.getInstance();
            tempRecipe.setTitle(i + ". Rezept");
            cookbook.addRecipe(tempRecipe);
        }
    }
}
