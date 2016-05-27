package sample.builder;


import org.apache.commons.io.FileUtils;
import org.apache.velocity.texen.util.FileUtil;
import org.hamcrest.Matcher;
import org.junit.Test;
import sample.config.IConfig;
import sample.model.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
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
        grichenland.setName("Griechenland");
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

        r1.setTitle("Rezepttitel1");
        r2.setTitle("Rezepttitel2");
        r3.setTitle("Rezepttitel3");
        r4.setTitle("Rezepttitel4");

        r1.add("Zutat1",5,"g");
        r2.add("Zutat2",8,"g");
        r3.add("Zutat3",6,"g");
        r4.add("Zutat4",4,"g");

        cookbook.addRecipe(r1);
        cookbook.addRecipe(r2);
        cookbook.addRecipe(r3);
        cookbook.addRecipe(r4);

        pdfBuilder.build(cookbook);
        String texFile= FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + cookbook.getTitle() + ".tex"));

        //Check generated text for r1
        assertThat(texFile,containsString("\\chead{Vorspeise}"));
        assertThat(texFile,containsString("\\lfoot{Vorspeise.Griechenland}"));
        assertThat(texFile,containsString("\\rfoot{Vorspeise.Griechenland}"));
        assertThat(texFile,containsString("\\item {Zutat1 5 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(System.getProperty("user.home") +"/.recipes2pdf/images/Rezepttitel11" );
        assertThat(texFile,stringContainsInOrder(substrings));
        assertThat(texFile,containsString("Rezepttext 1"));

        //Check generated text for r2
        assertThat(texFile,containsString("\\chead{Nachspeise}"));
        assertThat(texFile,containsString("\\lfoot{Nachspeise.Mexiko}"));
        assertThat(texFile,containsString("\\rfoot{Nachspeise.Mexiko}"));
        assertThat(texFile,containsString("\\item {Zutat2 8 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(System.getProperty("user.home") +"/.recipes2pdf/images/Rezepttitel22" );
        assertThat(texFile,stringContainsInOrder(substrings));
        assertThat(texFile,containsString("Rezepttext 2"));

        //Check generated text for r3
        assertThat(texFile,containsString("\\chead{Hauptspeise}"));
        assertThat(texFile,containsString("\\lfoot{Hauptspeise.China}"));
        assertThat(texFile,containsString("\\rfoot{Hauptspeise.China}"));
        assertThat(texFile,containsString("\\item {Zutat3 6 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(System.getProperty("user.home") +"/.recipes2pdf/images/Rezepttitel33" );
        assertThat(texFile,stringContainsInOrder(substrings));
        assertThat(texFile,containsString("Rezepttext 3"));


        //Check generated text for r4
        assertThat(texFile,containsString("\\chead{Vorspeise}"));
        assertThat(texFile,containsString("\\lfoot{Vorspeise.Mexiko}"));
        assertThat(texFile,containsString("\\rfoot{Vorspeise.Mexiko}"));
        assertThat(texFile,containsString("\\item {Zutat4 4 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(System.getProperty("user.home") +"/.recipes2pdf/images/Rezepttitel44" );
        assertThat(texFile,stringContainsInOrder(substrings));
        assertThat(texFile,containsString("Rezepttext 4"));



    }

    @Test
    public void testRecipePdfBuilder() throws Throwable {
        IConfig config = IConfig.getInstance();
        IConcreteBuilder pdfBuilder = new PdfBuilder(config);
        Category vorspeise = new Category();
        Region grichenland = new Region();
        IRecipe r1 = new Recipe();

        Season fruehling = new Season();
        fruehling.setName("Fruehling");
        vorspeise.setName("Vorspeise");
        grichenland.setName("Griechenland");
        r1.setCategory(vorspeise);
        r1.setSeason(fruehling);
        r1.setRegion(grichenland);
        r1.setText("Rezepttext 1");
        r1.setTitle("Testrezept");
        r1.add("Zutat1",5,"g");
        r1.setID(1L);

        pdfBuilder.build(r1);
        String texFile= FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + r1.getTitle() + ".tex"));

        //Check generated Text
        assertThat(texFile,containsString("\\chead{Vorspeise}"));
        assertThat(texFile,containsString("\\lfoot{Vorspeise.Griechenland}"));
        assertThat(texFile,containsString("\\rfoot{Vorspeise.Griechenland}"));
        assertThat(texFile,containsString("\\item {Zutat1 5 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(System.getProperty("user.home") +"/.recipes2pdf/images/Testrezept1" );
        assertThat(texFile,stringContainsInOrder(substrings));
        assertThat(texFile,containsString("Rezepttext 1"));

    }
}
