package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.model.IIngredient;
import sample.model.IUnit;
import sample.model.dummy.DummyRecipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.TreeMap;

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

        converter.replace("ingredientList", createIngredientList());
        converter.replace("centerHead", recipe_obj.getCategory());
        converter.replace("referenceNumber", recipe_obj.getCategoryNumber());
        converter.replace("imgPath", getOutputImage(recipe_obj.getID()).getAbsolutePath());
        converter.replace("recipeText", recipe_obj.getRecipeText());
        try {
            converter.parse(getTemplateFile(), outputTexFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getTemplateFile() {
        File template = new File(this.getClass().getClassLoader().getResource(Config.RESSOURCE_PATH + File.separator + Config.TEMPLATE_FOLDER_NAME + File.separator + Config.TEMPLATE_FILE_NAME).getPath());
        System.out.println(template.getAbsolutePath());
        return template;
    }

    private File getParserRootDir() {
        return new File(Config.RESSOURCE_USER_PATH);
    }

    private File getOutputImage(Long ImageID) {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + "_out" + Config.IMAGE_FILETYPE);
    }

    private File getInputImage(Long ImageID) {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + Config.IMAGE_FILETYPE);
    }

    private File getTempDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.TEMP_FOLDER_NAME);
    }

    private File getTemplateDir() {
        return getTemplateFile().getParentFile();
    }

    private File getOutputDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.OUTPUT_FOLDER_NAME);
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

//    private Map<IIngredient, Map<IUnit, String> > createIngredientList() {
//        Map<IIngredient, String> ingredientList = new TreeMap<>();
//        IIngredient ingred = IIngredient.getInstance();
//        ingred.setName("DerName");
//
//        ingredientList.put(ingred , "Key1");
//        return ingredientList;
//    }

    public Map<IIngredient, Map<Integer, IUnit>> createIngredientList() {
        TreeMap<IIngredient, Map<Integer, IUnit>> map = new TreeMap<>();
        TreeMap<Integer, IUnit> innerMap = new TreeMap<Integer, IUnit>();


        IIngredient eier = IIngredient.getInstance();
        eier.setName("Eier");

        IUnit stueck = IUnit.getInstance();
        stueck.setName("Stück");

        innerMap.put(5, stueck);
        map.put(eier, innerMap);

        return map;
    }
}
