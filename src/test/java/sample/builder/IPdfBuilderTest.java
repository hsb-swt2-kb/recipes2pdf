package sample.builder;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;
import sample.builder.pdfBuilder.PdfBuilder;
import sample.builder.pdfBuilder.PdfBuilderConfig;
import sample.config.IConfig;
import sample.model.ICookbook;
import sample.model.IRecipe;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class IPdfBuilderTest {
    @Test

    public void testRecipePdfBuilder() throws Throwable {
        ArrayList<IBuilder> builderList = new ArrayList<>();
        IBuilder pdfBuilder = new PdfBuilder(new PdfBuilderConfig(IConfig.getInstance()));
        builderList.add(pdfBuilder);
        BuilderController builderController = new BuilderController(builderList);

        ICookbook cookbook = ICookbook.getInstance();

        for (int i = 1; i <= 10; i++) {
            IRecipe tempRecipe = IRecipe.getInstance();
            tempRecipe.setTitle(i + ". Rezept");
            cookbook.addRecipe(tempRecipe);
        }

        File recipePDF = builderController.build(cookbook,"pdf");
        PDDocument pdfDoc;

        pdfDoc = PDDocument.load(recipePDF);

        PDFTextStripper stripper = new PDFTextStripper("ISO-8859-1");
        stripper.setStartPage(3); //Start extracting from page 3
        stripper.setEndPage(4); //Extract till page 5
        System.out.println(stripper.getText(pdfDoc));

    }


    public void testCookbookPdfBuilder() {
        IBuilder builder = new PdfBuilder(new PdfBuilderConfig(IConfig.getInstance()));


    }
}
