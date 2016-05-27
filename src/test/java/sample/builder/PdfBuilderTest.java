package sample.builder;


import org.junit.Test;
import sample.config.IConfig;
import sample.model.*;

import java.io.File;

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


        Category vorspeise = new Category();
        Category hauptspeise = new Category();
        Category grundrezept = new Category();
        Category nachspeise = new Category();

        Region china = new Region();
        Region mexiko = new Region();
        Region grichenland = new Region();
        Region frankreich = new Region();

        IRecipe r1 = new Recipe();
        IRecipe r2 = new Recipe();
        IRecipe r3 = new Recipe();
        IRecipe r4 = new Recipe();

        r1.setID(1L);
        r2.setID(2L);
        r3.setID(3L);
        r4.setID(4L);

        Season fruehling = new Season();
        Season sommer = new Season();
        Season herbst = new Season();
        Season winter = new Season();


        fruehling.setName("Fruehling");
        sommer.setName("Sommer");
        herbst.setName("Herbst");
        winter.setName("Winter");

        vorspeise.setName("Vorspeise");
        hauptspeise.setName("Hauptspeise");
        grundrezept.setName("Grundrezept");
        nachspeise.setName("Nachspeise");

        china.setName("China");
        mexiko.setName("Mexiko");
        grichenland.setName("Grichenland");
        frankreich.setName("Frankreich");


        r1.setCategory(vorspeise);
        r2.setCategory(nachspeise);
        r3.setCategory(hauptspeise);
        r4.setCategory(vorspeise);

        r1.setSeason(fruehling);
        r2.setSeason(sommer);
        r3.setSeason(fruehling);
        r4.setSeason(winter);

        r1.setRegion(grichenland);
        r2.setRegion(mexiko);
        r3.setRegion(china);
        r4.setRegion(mexiko);

        r1.setText("Rezepttext 1");
        r2.setText("Rezepttext 2");
        r3.setText("Rezepttext 3");
        r4.setText("Rezepttext 4");

        r1.setTitle("Rezepttitel 1");
        r2.setTitle("Rezepttitel 2");
        r3.setTitle("Rezepttitel 3");
        r4.setTitle("Rezepttitel 4");

        r1.add("Zutat 1",5,"g");
        r2.add("Zutat 2",8,"g");
        r3.add("Zutat 3",6,"g");
        r4.add("Zutat 4",4,"g");

        cookbook.addRecipe(r1);
        cookbook.addRecipe(r2);
        cookbook.addRecipe(r3);
        cookbook.addRecipe(r4);

        pdfBuilder.build(cookbook);
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
