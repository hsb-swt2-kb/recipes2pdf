package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.model.dummy.DummyRecipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Kai Nortmann
 */
class PdfBuilder {
    void buildPDF() {
        DummyRecipe recipe_obj = new DummyRecipe();
        File outputTexFile = getOutputTexFile();
        createImage(recipe_obj.getID());

        parseTexFile(outputTexFile, recipe_obj);
        createPDFFile(outputTexFile);
    }

    private void createPDFFile(File outputTexFile) {
        JLRGenerator generator = new JLRGenerator();

        try {
            generator.generate(outputTexFile, getOutputDir(), getParserRootDir());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseTexFile(File outputTexFile, DummyRecipe recipe_obj) {
        JLRConverter converter = new JLRConverter(getTemplateDir());

        converter.replace("ingredientList", recipe_obj.getIngredients());
        converter.replace("centerHead", recipe_obj.getCategory());
        converter.replace("referenceNumber", recipe_obj.getCategoryNumber());
        converter.replace("imgPath", getOutputImage(recipe_obj.getID()).getAbsolutePath());
        converter.replace("recipeText", recipe_obj.getRecipeText());
    }

    private File getTemplateFile() {
        return new File(this.getClass().getClassLoader().getResource(Config.RESSOURCEPATH + File.separator + Config.TEMPLATE_FOLDER_NAME + File.separator + Config.TEMPLATE_FILE_NAME).getPath());
    }

    private File getParserRootDir() {
        return new File(Config.RESSOURCEPATH);
    }

    private File getOutputImage(Long ImageID) {
        return new File(Config.RESSOURCEPATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + "_out" + Config.IMAGE_FILETYPE);
    }

    private File getInputImage(Long ImageID) {
        return new File(Config.RESSOURCEPATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + Config.IMAGE_FILETYPE);
    }

    private File getTempDir() {
        return new File(Config.RESSOURCEPATH + File.separator + Config.TEMP_FOLDER_NAME);
    }

    private File getTemplateDir() {
        return new File(Config.RESSOURCEPATH + File.separator + Config.TEMPLATE_FOLDER_NAME);
    }

    private File getOutputDir() {
        return new File(Config.RESSOURCEPATH + File.separator + Config.OUTPUT_FOLDER_NAME);
    }

    private File getOutputTexFile() {
        return new File(getTempDir().getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "1" + Config.OUTPUT_FILETYPE);
    }

    private void createImage(Long recipeID) {
        try {
            byte[] img = Files.readAllBytes(getInputImage(recipeID).toPath());
            FileOutputStream outputStream = new FileOutputStream(getOutputImage(recipeID));
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
