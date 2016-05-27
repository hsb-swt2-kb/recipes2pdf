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

    private void parseTexFile(File outputTexFile, ICookbook cookbook) throws Exception {
        JLRConverter converter = new JLRConverter(config.getTemplateDir());

        List<String> sortAttributes = new ArrayList();
        sortAttributes.add("category");
        sortAttributes.add("region");

        converter.replace("cookbook", cookbook);
        converter.replace("refNumList",generateRefNumList(cookbook.getRecipes(),sortAttributes));
        converter.replace("imgDir", config.getImageDir().getAbsolutePath());

        if (!converter.parse(config.getTemplateFile(), outputTexFile)) {
            throw new Exception("Convert template to " + config.getOutputTexFile(cookbook.getTitle()) + " failed! Error Message:\n" + converter.getErrorMessage()); //TODO: Display ErrorMessage in GUI?
        }
    }

    private File createPDFFile(File outputTexFile, ICookbook cookbook) throws Exception {

        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(outputTexFile, config.getOutputDir(), config.getParserRootDir())) {
            return config.getOutputPdfFile(cookbook.getTitle());
        } else {
            throw new Exception("Parse \"" + config.getOutputTexFile(cookbook.getTitle()) + "\" to \"" + config.getOutputPdfFile(cookbook.getTitle()) + "\" failed! Error Message:\n" + generator.getErrorMessage());

        }
    }


    private byte[] createImageByteArray(File image)
    {
        try {
            return Files.readAllBytes(config.getDefaultImage().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void createImage(IRecipe recipe) throws  Exception{
        try {
            //byte[] img = Files.readAllBytes(config.getInputImage().toPath());
            byte[] img = Optional.ofNullable(recipe.getImage()).orElseGet(() -> createImageByteArray(config.getDefaultImage()));

            FileOutputStream outputStream = new FileOutputStream(config.getImage("tmpRecipeImage.jpg"));
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File build(ICookbook cookbook) throws Exception {
        File outputTexFile = config.getOutputTexFile(cookbook.getTitle());
        Cookbook myCookbook = (Cookbook) cookbook;
        List<String> sortLevelList = new ArrayList();
        sortLevelList.add("region");
        sortLevelList.add("category");
        myCookbook.setRecipes(RecipeListSorter.sort(myCookbook.getRecipes(),sortLevelList)); //TODO: cookbook.getSortLevelChain


        for (IRecipe recipe : myCookbook.getRecipes()) {
            createImage(recipe);
        }
        parseTexFile(outputTexFile, myCookbook);

        return createPDFFile(outputTexFile, myCookbook);
    }

    public File build(IRecipe recipe) throws Exception {
        ICookbook cookbook = new Cookbook();
        cookbook.setTitle(recipe.getTitle());
        cookbook.addRecipe(recipe);

        File outputTexFile = config.getOutputTexFile(recipe.getTitle());

        createImage(recipe);

        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }


    private List<String> generateRefNumList(List<IRecipe> recipes,List<String> sortChain) {
        List<String> refNumList = new ArrayList();
        List<Properties> propList = generatePropertyList(recipes);
        String refNum = "";

        for (int i=0;i<recipes.size();i++){
            refNum = "";
            for(String sortLevel : sortChain){
                refNum += propList.get(i).getProperty(sortLevel) + "." ;
            }
            refNumList.add(refNum.substring(0, refNum.length()-1));
        }
        return refNumList;
    }

    private List<Properties> generatePropertyList(List<IRecipe> recipes) {
        List<Properties> propList = new ArrayList();
        Properties props = new Properties();
        for(IRecipe recipe : recipes)
        {

            props.setProperty("category",(recipe.getCategory()==null) ? "" : recipe.getCategory().getName());
            props.setProperty("season",(recipe.getSeason()==null) ? "" : recipe.getSeason().getName());
            props.setProperty("nurture",(recipe.getNurture()==null) ? "" : recipe.getNurture().getName());
            props.setProperty("curse",(recipe.getCourse()==null) ? "" : recipe.getCourse().getName());
            props.setProperty("region",(recipe.getRegion()==null) ? "" : recipe.getRegion().getName());
            propList.add(props);
        }
        return propList;
    }

    @Override
    public boolean builds(String filetype) {
        return filetype.equalsIgnoreCase("pdf");
    }
}
