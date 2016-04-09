package sample.pdfBuilder;


import org.junit.Test;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.pdfBuilder.exceptions.ConvertTemplatetoTexFailedException;
import sample.pdfBuilder.exceptions.GeneratePdfFailedException;

import java.io.File;
import java.io.IOException;

public class pdfBuilderTest {
    @Test
    public void testPdfBuider() {
        PdfBuilder builder = new PdfBuilder();
        ICookbook cookbook = ICookbook.getInstance();

        for (int i = 1; i <= 10; i++) {
            IRecipe tempRecipe = IRecipe.getInstance();
            tempRecipe.setTitle(i + ". Rezept");
            cookbook.addRecipe(tempRecipe);
        }

        IRecipe recipe = IRecipe.getInstance();
        recipe.setTitle("Mein Rezept");

        try {
            File cookbookPDF = builder.buildPDF(ICookbook.getInstance());

            File recipePDF = builder.buildPDF(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneratePdfFailedException e) {
            e.printStackTrace();
        } catch (ConvertTemplatetoTexFailedException e) {
            e.printStackTrace();
        }
    }
}
