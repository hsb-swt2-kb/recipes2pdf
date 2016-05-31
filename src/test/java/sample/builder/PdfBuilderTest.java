package sample.builder;


<<<<<<< HEAD
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
=======
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.exception.ParseErrorException;
>>>>>>> master
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

<<<<<<< HEAD
    public void testRecipePdfBuilder() throws Throwable {
=======
    /**
     * Test, if a single Recipe is build correctly
     *
     * @throws Exception
     */
    @Test
    public void testRecipePdfBuilder() throws Exception {
>>>>>>> master
        IConfig config = IConfig.getInstance();

<<<<<<< HEAD
        IConcreteBuilder pdfBuilder = new PdfBuilder(config);
=======
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
        ICookbook cookbook = new Cookbook();
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
>>>>>>> master

        IRecipe recipe = new Recipe();
        recipe.setTitle("DasRezept");

        File recipePDF = pdfBuilder.build(recipe);

        assertFalse(new File(config.getProperty("PROGRAM_USERDATA_DIR") + config.getProperty("OUTPUT_FOLDER_NAME") + File.separator + recipe.getTitle() + ".pdf").exists());
    }
}
