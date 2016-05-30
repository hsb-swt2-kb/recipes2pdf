package sample.builder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import sample.builder.Exceptions.TemplateConverterException;
import sample.builder.Exceptions.TexParserException;
import sample.config.IConfig;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.ISortlevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Kai Nortmann
 */

public class PdfBuilder implements IConcreteBuilder {


    private final PdfBuilderConfig config;

    public PdfBuilder(IConfig config) {
        this.config = new PdfBuilderConfig(config);
    }

    private void parseTexFile(File outputTexFile, File templateFile, File imageDir, ICookbook cookbook) throws IOException, TemplateConverterException {
        JLRConverter converter = new JLRConverter(templateFile.getParentFile());
        List <ISortlevel> sortAttributeChain = cookbook.getSortlevel();
        ((Cookbook)cookbook).setRecipes(RecipeListSorter.sort(cookbook.getRecipes(), sortAttributeChain));

        converter.replace("cookbook", cookbook);
        converter.replace("refNumList", generateRefNumList(cookbook.getRecipes(), sortAttributeChain));
        converter.replace("imgDir", toLatexStylePath(imageDir.getAbsolutePath()));

        if (!converter.parse(templateFile, outputTexFile)) {
            throw new TemplateConverterException("Convert template to " + outputTexFile + " failed! Error Message:\n" + converter.getErrorMessage()); //TODO: Display ErrorMessage in GUI?
        }
    }

    private String toLatexStylePath(String path) {
        return FilenameUtils.separatorsToUnix(path);
    }

    private File createPDFFile(File outputTexFile, File outputPDFFile, File rootDir) throws IOException, TexParserException {

        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(outputTexFile, outputTexFile.getParentFile(), rootDir)) {
            return outputPDFFile;
        } else {
            throw new TexParserException("Parse \"" + outputTexFile + "\" to \"" + outputPDFFile + "\" failed! Error Message:\n" + generator.getErrorMessage());
        }
    }

    private void createAllImages(ICookbook cookcook, File imgDir){
        for (IRecipe recipe : cookcook.getRecipes()) {
            createImage(recipe, imgDir);
        }
    }

    private void createImage(IRecipe recipe, File imgDir){
        try {
            byte[] img = (recipe.getImage() == null) ? defaultImagetoByteArray() : recipe.getImage();

            FileOutputStream outputStream = new FileOutputStream(new File(imgDir + File.separator + recipe.getTitle() + recipe.getID()) + ".jpg");
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] defaultImagetoByteArray() throws IOException {
        return IOUtils.toByteArray(this.getClass().getResourceAsStream("images/default_image.jpg"));
    }


    public File build(ICookbook cookbook) throws IOException, TemplateConverterException, TexParserException {

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
    @Override
    public File build(IRecipe recipe, List<ISortlevel> sortLevels) throws IOException, TemplateConverterException, TexParserException {
        ICookbook myCookbook = new Cookbook();
        myCookbook.setTitle(recipe.getTitle());
        myCookbook.addRecipe(recipe);
        ((Cookbook)myCookbook).setSortlevel(sortLevels);

        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createImage(recipe, imgDir);

        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);

    }

    public File build(IRecipe recipe) throws IOException, TemplateConverterException, TexParserException {
        ICookbook myCookbook = new Cookbook();
        myCookbook.setTitle(recipe.getTitle());
        myCookbook.addRecipe(recipe);
        List<ISortlevel> sortlevels = new ArrayList<>();

        ((Cookbook)myCookbook).setSortlevel(sortlevels);

        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createImage(recipe, imgDir);

        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);

    }


    private Map<IRecipe, String> generateRefNumList(List<IRecipe> recipes, List<ISortlevel> sortChain) {
        Map<IRecipe, String> refNumList = new HashMap<>();
        Map<IRecipe, Properties> propList = generateRecipePropertyList(recipes);
        String refNum = "";

        for (IRecipe recipe : recipes) {
            refNum = "";
            for (ISortlevel sortLevel : sortChain) {
                refNum += propList.get(recipe).getProperty(sortLevel.getName()) + ".";
            }

            refNumList.put(recipe,(refNum =="") ? "" : refNum.substring(0, refNum.length() - 1));
        }
        return refNumList;
    }

    private Map<IRecipe,Properties> generateRecipePropertyList(List<IRecipe> recipes) { //TODO: Meybe get the List of Sortable Attributes out of database and work with reflections on recipe?
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

