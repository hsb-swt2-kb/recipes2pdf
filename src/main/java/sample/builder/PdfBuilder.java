package sample.builder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.config.IConfig;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.Recipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Kai Nortmann
 */

public class PdfBuilder implements IConcreteBuilder {


    private final PdfBuilderConfig config;

    public PdfBuilder(IConfig config) {
        this.config = new PdfBuilderConfig(config);
    }

    private void parseTexFile(File outputTexFile, File templateFile, File imageDir, ICookbook cookbook) throws Exception {
        JLRConverter converter = new JLRConverter(templateFile.getParentFile());

        List<String> sortAttributes = new ArrayList(); //TODO: Get this List out of Database (attribute of cookbook)
        sortAttributes.add("category");
        sortAttributes.add("region");

        ((Cookbook)cookbook).setRecipes(RecipeListSorter.sort(cookbook.getRecipes(), sortAttributes));

        converter.replace("cookbook", cookbook);
        converter.replace("refNumList", generateRefNumList(cookbook.getRecipes(), sortAttributes));
        converter.replace("imgDir", imageDir.getAbsolutePath());

        if (!converter.parse(templateFile, outputTexFile)) {
            throw new Exception("Convert template to " + outputTexFile + " failed! Error Message:\n" + converter.getErrorMessage()); //TODO: Display ErrorMessage in GUI?
        }
    }

    private File createPDFFile(File outputTexFile, File outputPDFFile, File rootDir) throws Exception {

        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(outputTexFile, outputTexFile.getParentFile(), rootDir)) {
            return outputPDFFile;
        } else {
            throw new Exception("Parse \"" + outputTexFile + "\" to \"" + outputPDFFile + "\" failed! Error Message:\n" + generator.getErrorMessage());
        }
    }


    private byte[] createImageByteArray(File image) {
        try {
            return Files.readAllBytes(config.getDefaultImage().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createAllImages(ICookbook cookcook, File imgDir) throws Exception {
        for (IRecipe recipe : cookcook.getRecipes()) {
            createImage(recipe, imgDir);
        }
    }

    private void createImage(IRecipe recipe, File imgDir) throws Exception {
        try {
            //byte[] img = Files.readAllBytes(config.getInputImage().toPath());
            byte[] img = Optional.ofNullable(recipe.getImage()).orElseGet(() -> createImageByteArray(config.getDefaultImage()));

            FileOutputStream outputStream = new FileOutputStream(new File(imgDir + File.separator + recipe.getTitle() + recipe.getID()) + ".jpg");
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File build(ICookbook cookbook) throws Exception {

        Cookbook myCookbook = (Cookbook) cookbook;

        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createAllImages(myCookbook, imgDir);
        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);
    }

    public File build(IRecipe recipe) throws Exception {
        ICookbook myCookbook = new Cookbook();
        myCookbook.setTitle(recipe.getTitle());
        myCookbook.addRecipe(recipe);


        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createImage(recipe, imgDir);

        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);

    }


    private Map<IRecipe, String> generateRefNumList(List<IRecipe> recipes, List<String> sortChain) {
        Map<IRecipe, String> refNumList = new HashMap<>();
        Map<IRecipe, Properties> propList = generatePropertyList(recipes);
        String refNum = "";

        for (IRecipe recipe : recipes) {
            refNum = "";
            for (String sortLevel : sortChain) {
                refNum += propList.get(recipe).getProperty(sortLevel) + ".";
            }
            refNumList.put(recipe, refNum.substring(0, refNum.length() - 1));
        }
        return refNumList;
    }

    private Map<IRecipe,Properties> generatePropertyList(List<IRecipe> recipes) { //TODO: Meybe get the List of Sortable Attributes out of database and work with reflections on recipe?
        Map<IRecipe,Properties> propList = new HashMap<>();

        for (IRecipe recipe : recipes) {
            Properties props = new Properties();
            props.setProperty("category", (recipe.getCategory() == null) ? "" : recipe.getCategory().getName());
            props.setProperty("season", (recipe.getSeason() == null) ? "" : recipe.getSeason().getName());
            props.setProperty("nurture", (recipe.getNurture() == null) ? "" : recipe.getNurture().getName());
            props.setProperty("curse", (recipe.getCourse() == null) ? "" : recipe.getCourse().getName());
            props.setProperty("region", (recipe.getRegion() == null) ? "" : recipe.getRegion().getName());
            propList.put(recipe,props);
        }
        return propList;
    }

    @Override
    public boolean builds(String filetype) {
        return filetype.equalsIgnoreCase("pdf");
    }
}

