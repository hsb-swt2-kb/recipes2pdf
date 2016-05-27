package sample.builder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.config.IConfig;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

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

        System.out.println(imgDir);

        List<String> sortLevelList = new ArrayList(); //TODO: get this List out of Cookbook
        sortLevelList.add("region");
        sortLevelList.add("category");
        myCookbook.setRecipes(RecipeListSorter.sort(myCookbook.getRecipes(), sortLevelList));

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


    private List<String> generateRefNumList(List<IRecipe> recipes, List<String> sortChain) {
        List<String> refNumList = new ArrayList();
        List<Properties> propList = generatePropertyList(recipes);
        String refNum = "";

        for (int i = 0; i < recipes.size(); i++) {
            refNum = "";
            for (String sortLevel : sortChain) {
                refNum += propList.get(i).getProperty(sortLevel) + ".";
            }
            refNumList.add(refNum.substring(0, refNum.length() - 1));
        }
        return refNumList;
    }

    private List<Properties> generatePropertyList(List<IRecipe> recipes) { //TODO: Meybe get the List of Sortable Attributes out of database and work with reflections on recipe?
        List<Properties> propList = new ArrayList();
        Properties props = new Properties();
        for (IRecipe recipe : recipes) {
            props.setProperty("category", (recipe.getCategory() == null) ? "" : recipe.getCategory().getName());
            props.setProperty("season", (recipe.getSeason() == null) ? "" : recipe.getSeason().getName());
            props.setProperty("nurture", (recipe.getNurture() == null) ? "" : recipe.getNurture().getName());
            props.setProperty("curse", (recipe.getCourse() == null) ? "" : recipe.getCourse().getName());
            props.setProperty("region", (recipe.getRegion() == null) ? "" : recipe.getRegion().getName());
            propList.add(props);
        }
        return propList;
    }

    @Override
    public boolean builds(String filetype) {
        return filetype.equalsIgnoreCase("pdf");
    }
}

