package sample.builder;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.exception.ParseErrorException;
import org.junit.Test;
import sample.builder.Exceptions.TexParserException;
import sample.config.IConfig;
import sample.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        r1.setSeason(fruehling);
        r1.setRegion(grichenland);
        r1.setText("Rezepttext 1");
        r1.setTitle("Rezepttitel1");
        r1.add("Zutat1", 5, "g");
        r1 = generateRecipe("Rezepttitel1", "Rezepttext 1", 1L, "Vorspeise", "Griechenland", "Frühling", "Zutat1", 5, "g");

        r2.setCategory(nachspeise);
        r2.setSeason(sommer);
        r2.setRegion(mexiko);
        r2.setText("Rezepttext 2");
        r2.setTitle("Rezepttitel2");
        r2.add("Zutat2", 8, "g");
        r2 = generateRecipe("Rezepttitel2", "Rezepttext 2", 2L, "Nachspeise", "Mexiko", "Sommer", "Zutat2", 8, "g");


        r3.setCategory(hauptspeise);
        r3.setSeason(fruehling);
        r3.setRegion(china);
        r3.setText("Rezepttext 3");
        r3.setTitle("Rezepttitel3");
        r3.add("Zutat3", 6, "g");
        r3 = generateRecipe("Rezepttitel3", "Rezepttext 3", 3L, "Hauptspeise", "China", "Frühling", "Zutat3", 6, "g");

        r4.setCategory(vorspeise);
        r4.setSeason(winter);
        r4.setRegion(mexiko);
        r4.setText("Rezepttext 4");
        r4.setTitle("Rezepttitel4");
        r4.add("Zutat4", 4, "g");
        r4 = generateRecipe("Rezepttitel4", "Rezepttext 4", 4L, "Vorspeise", "Mexiko", "Winter", "Zutat4", 4, "g");


        cookbook.addRecipe(r1);
        cookbook.addRecipe(r2);
        cookbook.addRecipe(r3);
        cookbook.addRecipe(r4);
        ((Cookbook) cookbook).setSortlevel(generateSortlevelList("category", "region"));
        pdfBuilder.build(cookbook);
        String texFile = FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + cookbook.getTitle() + ".tex"));

        //Check generated text for r1
        assertThat(texFile, containsString("\\chead{Vorspeise}"));
        assertThat(texFile, containsString("\\lfoot{Vorspeise.Griechenland}"));
        assertThat(texFile, containsString("\\rfoot{Vorspeise.Griechenland}"));
        assertThat(texFile, containsString("\\item {Zutat1 5.0 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Rezepttitel11");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 1"));

        //Check generated text for r2
        assertThat(texFile, containsString("\\chead{Nachspeise}"));
        assertThat(texFile, containsString("\\lfoot{Nachspeise.Mexiko}"));
        assertThat(texFile, containsString("\\rfoot{Nachspeise.Mexiko}"));
        assertThat(texFile, containsString("\\item {Zutat2 8.0 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Rezepttitel22");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 2"));

        //Check generated text for r3
        assertThat(texFile, containsString("\\chead{Hauptspeise}"));
        assertThat(texFile, containsString("\\lfoot{Hauptspeise.China}"));
        assertThat(texFile, containsString("\\rfoot{Hauptspeise.China}"));
        assertThat(texFile, containsString("\\item {Zutat3 6.0 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Rezepttitel33");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 3"));


        //Check generated text for r4
        assertThat(texFile, containsString("\\chead{Vorspeise}"));
        assertThat(texFile, containsString("\\lfoot{Vorspeise.Mexiko}"));
        assertThat(texFile, containsString("\\rfoot{Vorspeise.Mexiko}"));
        assertThat(texFile, containsString("\\item {Zutat4 4.0 g}"));
        substrings.clear();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Rezepttitel44");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 4"));
    }

    /**
     * Test, if a single Recipe is build correctly
     *
     * @throws Exception
     */
    @Test
    public void testRecipePdfBuilder() throws Exception {
        IConfig config = IConfig.getInstance();
        IConcreteBuilder pdfBuilder = new PdfBuilder(config);

        IRecipe r1 = generateRecipe("Testrezept", "Rezepttext 1", 1L, "Vorspeise", "Griechenland", "Frühling", "Zutat1", 5, "g");
        List<ISortlevel> sortlevels = generateSortlevelList("category", "region", "season");
        pdfBuilder.build(r1, sortlevels);
        String texFile = FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + r1.getTitle() + ".tex"));

        //Check generated Text
        assertThat(texFile, containsString("\\chead{Vorspeise}"));
        assertThat(texFile, containsString("\\lfoot{Vorspeise.Griechenland.Frühling}"));
        assertThat(texFile, containsString("\\rfoot{Vorspeise.Griechenland.Frühling}"));
        assertThat(texFile, containsString("\\item {Zutat1 5.0 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Testrezept1");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 1"));

    }

    /**
     * Test, if a single Recipe throws a ParseErrorException if the templatefile has incorrect latex syntax
     */
    @Test(expected = ParseErrorException.class)
    public void testThrowConverterEsception() throws Exception {
        IConfig config = IConfig.getInstance();
        config.setProperty("TEMPLATE_FILE_NAME", "testTexFile.tex");
        InputStream templateStream = this.getClass().getResourceAsStream("wrongTestTemplateFile");
        File templateFile = new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("TEMPLATE_FOLDER_NAME") + File.separator + config.getProperty("TEMPLATE_FILE_NAME"));
        ParseErrorException ex = null;

        try {
            FileUtils.copyInputStreamToFile(templateStream, templateFile);
            PdfBuilder builder = new PdfBuilder(config);
            ICookbook cookbook = new Cookbook();
            cookbook.addRecipe(generateRecipe("Testrezept", "Testrezepttext", 1L, "Hauptspeise", "Deutschland", "Sommer", "Mehl", 400, "g"));
            builder.build(cookbook);
        } catch (ParseErrorException e) {
            ex = e;

        } finally {
            config.setProperty("TEMPLATE_FILE_NAME", "cookbookTemplate.tex");
            IOUtils.closeQuietly(templateStream);
            if (ex != null) {
                throw ex;
            }
        }

    }

    /**
     * Test, if a single Recipe throws a TexParserException if some attributes are null.
     *
     * @throws Exception
     */
    @Test(expected = TexParserException.class)
    public void testThrowParserEsception() throws Exception {
        IConfig config = IConfig.getInstance();
        PdfBuilder builder = new PdfBuilder(config);
        ICookbook cookbook = new Cookbook();
        cookbook.addRecipe(new Recipe());
        builder.build(cookbook);
    }

    /**
     * Tests if the footer and header are empty if no sortlevels are given
     * @throws Exception
     */
    @Test
    public void testRicipeNoSortlevel() throws Exception {
        IConfig config = IConfig.getInstance();
        PdfBuilder builder = new PdfBuilder(config);
        IRecipe r1 = generateRecipe("Testrezept", "Rezepttext 1", 1L, "Vorspeise", "Griechenland", "Frühling", "Zutat1", 5, "g");

        builder.build(r1);
        String texFile = FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + r1.getTitle() + ".tex"));

        //Check generated Text
        assertThat(texFile, containsString("\\chead{}"));
        assertThat(texFile, containsString("\\lfoot{}"));
        assertThat(texFile, containsString("\\rfoot{}"));
        assertThat(texFile, containsString("\\item {Zutat1 5.0 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Testrezept1");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 1"));
    }

    /**
     * Tests if building a recipe that has a german umlaut in its name does work correctly
     * @throws Exception
     */
    @Test
    public void testRecipeUmlautInName() throws Exception {
        IConfig config = IConfig.getInstance();
        PdfBuilder builder = new PdfBuilder(config);

        IRecipe r1 = generateRecipe("Germknödel", "Rezepttext 1", 1L, "Vorspeise", "Griechenland", "Frühling", "Zutat1", 5, "g");

        builder.build(r1);
        String texFile = FileUtils.readFileToString(new File(config.getProperty("PROGRAM_USERDATA_DIR") + File.separator + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + r1.getTitle() + ".tex"));

        //Check generated Text
        assertThat(texFile, containsString("\\chead{}"));
        assertThat(texFile, containsString("\\lfoot{}"));
        assertThat(texFile, containsString("\\rfoot{}"));
        assertThat(texFile, containsString("\\item {Zutat1 5.0 g}"));
        List<String> substrings = new ArrayList<>();
        substrings.add("\\includegraphics[width=\\linewidth]{");
        substrings.add(FilenameUtils.separatorsToUnix(System.getProperty("user.home")) + "/.recipes2pdf/images/Germknödel");
        assertThat(texFile, stringContainsInOrder(substrings));
        assertThat(texFile, containsString("Rezepttext 1"));
    }

    /**
     * tests if the builder returns true for diffrent cases of 'pdf'
     * @throws Exception
     */
    @Test
    public void testBuildsMethod() throws Exception {
        IConfig config = IConfig.getInstance();
        PdfBuilder builder = new PdfBuilder(config);
        assertTrue(builder.builds("pdf"));
        assertTrue(builder.builds("pDf"));
        assertTrue(builder.builds("PDF"));

    }

    IRecipe generateRecipe(String title, String text, Long id, String category, String region, String season, String ingredientName, Integer ingrentAmount, String ingredientUnit) {
        IRecipe recipe = new Recipe();
        ICategory testCategory = new Category();
        IRegion testRegion = new Region();
        ISeason testSeason = new Season();

        testCategory.setName(category);
        testRegion.setName(region);
        testSeason.setName(season);

        recipe.setTitle(title);
        recipe.setText(text);
        recipe.setID(id);
        recipe.setCategory(testCategory);
        recipe.setRegion(testRegion);
        recipe.setSeason(testSeason);
        recipe.add(ingredientName, ingrentAmount, ingredientUnit);
        return recipe;
    }

    List<ISortlevel> generateSortlevelList(String primaryLevel, String secondaryLevel, String thirdLevel) {
        List<ISortlevel> sortlevelList = new ArrayList<>();
        sortlevelList.add(generateSortlevel(primaryLevel));
        sortlevelList.add(generateSortlevel(secondaryLevel));
        sortlevelList.add(generateSortlevel(thirdLevel));
        return sortlevelList;
    }

    List<ISortlevel> generateSortlevelList(String primaryLevel, String secondaryLevel) {
        List<ISortlevel> sortlevelList = new ArrayList<>();
        sortlevelList.add(generateSortlevel(primaryLevel));
        sortlevelList.add(generateSortlevel(secondaryLevel));
        return sortlevelList;
    }

    ISortlevel generateSortlevel(String sortlevelName) {
        ISortlevel sortlevel = new Sortlevel();
        sortlevel.setName(sortlevelName);
        return sortlevel;
    }


}
