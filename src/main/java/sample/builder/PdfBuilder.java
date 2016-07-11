package sample.builder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.exception.ParseErrorException;
import sample.builder.Exceptions.TexParserException;
import sample.config.IConfig;
import sample.model.Cookbook;
import sample.model.Cookbook;
import sample.model.Recipe;
import sample.model.Sortlevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Kai Nortmann
 */

public class PdfBuilder implements IConcreteBuilder {


    private final PdfBuilderConfig config;

    /**
     * Constructor for a PdfBuilder. A PdfBuilder needs configurable information out of an IConfig file.
     *
     * @param config IConfig, that holds information needed for the building process. Default_config holds everything needed
     */
    public PdfBuilder(IConfig config) {
        this.config = new PdfBuilderConfig(config);
    }

    /**
     * This method creates a specific .tex file for a cookbook by injecting the cookbook recipes into an apache velocity .tex template. The used library JLR
     * can interpret velocity and injects the attributes of the cookbook and its containing recipes into the template-variables.
     * <h1>Precondition:</h1>
     * <ul>
     * <li>cookbook is not null</li>
     * <li>cookbooks has recipes</li>
     * <li>recipes contain following information:</li>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * .tex file for the cookbook is generated and is saved in the path that is given in outputTexFile
     *
     * @param outputTexFile .tex File that is beeing created.
     * @param templateFile  .tex template File that is used to crate the outputTexFile
     * @param imageDir      Directory, where the images for the recipes are saved. Each image is named with the title concatenated with the ID of the recipe it belongs to.
     * @param cookbook      Coobook that is converted to a PDF File
     * @throws IOException Is thrown by the JLRConverter while converting.
     */
    private void parseTexFile(File outputTexFile, File templateFile, File imageDir, Cookbook cookbook) throws IOException, ParseErrorException {
        JLRConverter converter = new JLRConverter(templateFile.getParentFile());
        List<Sortlevel> sortAttributeChain = cookbook.getSortlevel();
        cookbook.setRecipes(RecipeListSorter.sort(cookbook.getRecipes(), sortAttributeChain));

        converter.replace("cookbook", cookbook);
        converter.replace("refNumList", generateRefNumList(cookbook.getRecipes(), sortAttributeChain));
        converter.replace("imgDir", toLatexStylePath(imageDir.getAbsolutePath()));

        converter.parse(templateFile, outputTexFile);
    }

    /**
     * Is needed to make a Path matching the Latex syntax for paths
     *
     * @param path Path, that is converted to latex style path
     * @return path that can be used in a latex document without getting errors
     */
    private String toLatexStylePath(String path) {
        return FilenameUtils.separatorsToUnix(path);
    }

    /**
     * This Method is used to parse the specific .tex file, created by the converter into a PDF File. Therefore the The JLRGenerator implicitly uses
     * pdflatex.
     * <h1>Precondition:</h1>
     * <ul>
     * <li>Pdflatex installed</li>
     * <li>inputTexfile existing and has correct latex syntax</li>
     * <li>rootdir is the parent directory of the parentdirectory of the outputPDFFile</li>
     * <li>rootdir is the parent directory of the parentdirectory of the inputtexFile</li>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * Pdf file is generated and is saved in the path that is given by outputPDFFile
     *
     * @param inputTexFile  The texfile, that is used to generate the outputPDFFile
     * @param outputPDFFile specifies where the pdfFile should be saved
     * @param rootDir       is needed for the JLRGenerator
     * @return Fileobject that points to the generated PDFFile
     * @throws IOException        Is thrown by the JLRGenerator if any error occurs while generating the PDF File
     * @throws TexParserException Is thrown when the .tex file has wrong latex syntax and cannot be interpreted by pdflatex
     */
    private File createPDFFile(File inputTexFile, File outputPDFFile, File rootDir) throws IOException, TexParserException {
        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(inputTexFile, inputTexFile.getParentFile(), rootDir)) {
            return outputPDFFile;
        } else {
            throw new TexParserException("Parse \"" + inputTexFile + "\" to \"" + outputPDFFile + "\" failed! Error Message:\n" + generator.getErrorMessage() + "\nLogfile:" + FileUtils.readFileToString(new File(outputPDFFile.getParentFile().getAbsolutePath() + File.separator + FilenameUtils.getBaseName(outputPDFFile.getAbsolutePath()) + ".log")));
        }
    }

    /**
     * This Methos is needed to save all Recipe images to hdd. The saving is needed because the implicitly used latex compiler pdflatex needs the images
     * to be saved on a filesystem.
     * <h1>Precondition:</h1>
     * <ul>
     * <li>Cookbook has recipes</li>
     * <li>All recipes have an image (recommended, when image==null than a default image is taken)</li>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * Image files have the names of the recipe concatenated whith their id and are saved on hdd at the path that is given by imgDir
     *
     * @param cookcook cookbook that holds all recipes with teir images
     * @param imgDir   directory where the images are saved
     */
    private void createAllImages(Cookbook cookcook, File imgDir) {
        for (Recipe recipe : cookcook.getRecipes()) {
            createImage(recipe, imgDir);
        }
    }

    /**
     * This Methos is needed to save a single Recipe images to hdd. The saving is needed because the implicitly used latex compiler pdflatex needs the images
     * to be saved on a filesystem.
     * <h1>Precondition:</h1>
     * <ul>
     * <li>Recipe is not null</li>
     * <li>Recipe has an image (recommended, when image==null than a default image is taken)</li>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * Image file has the name of the recipe concatenated whith its id and is saved on hdd at the path that is given by imgDir
     *
     * @param recipe recipe that holds the image
     * @param imgDir Directory to save the image.
     */
    private void createImage(Recipe recipe, File imgDir) {
        try {
            byte[] img = (recipe.getImage() == null) ? defaultImagetoByteArray() : recipe.getImage();

            FileOutputStream outputStream = new FileOutputStream(new File(imgDir + File.separator + recipe.getTitle() + recipe.getID()) + ".jpg");
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a bytearrey of the defaultimage that is saved in the ressources
     *
     * @return bytearray of the ressoure sample/builder/images/default_image.jpg
     * @throws IOException is thrown when the ressouce does not exist
     */
    private byte[] defaultImagetoByteArray() throws IOException {
        return IOUtils.toByteArray(this.getClass().getResourceAsStream("images/default_image.jpg"));
    }


    /**
     * This Method builds one documnet containing Document out of the given cookbook.
     * <h1>Precondition:</h1>
     * Cookbook is not Null and has Recipes and Sortlevels(optional but recommendet)<br>
     * Recipes in the cookbook have to have all attributes, that are needed for the document building process.<br>
     * The needed attributes in recipes are:<br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param cookbook Cookbook, that is converted into a Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Converter, when anything with the Filesystem went wrong while converting the template to an explicit .tex for the cookbook
     * @return: File object, that points to the generated Document
     */
    @Override
    public File build(Cookbook cookbook) throws IOException, TexParserException {

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

    /**
     * This Method generates a single Recipe Document that has a header showing the primary sort level and footer showing the referendnumber)
     * <h1>Precondition:</h1>
     * Recipe is not Null and has following Attributes filled with values: <br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>     *
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param recipe     The recipe, that sould be converted into a Document
     * @param sortLevels A sorted List of Sortlevel. The recipe will get a Referencenumber according to the order of this List.
     * @return File object, that points to the generated Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Parser, when anything with the Filesystem went wrong while parsing the PDF File
     */
    @Override
    public File build(Recipe recipe, List<Sortlevel> sortLevels) throws IOException, TexParserException {
        Cookbook myCookbook = new Cookbook();
        myCookbook.setTitle(recipe.getTitle());
        myCookbook.addRecipe(recipe);
        ((Cookbook) myCookbook).setSortlevel(sortLevels);

        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createImage(recipe, imgDir);

        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);

    }

    /**
     * This Method generates a single Recipe Document that has no header (normally showing the primary sort level) and no footer (normally showing the reference number)
     * <h1>Precondition:</h1>
     * Recipe is not Null and has following Attributes filled with values: <br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param recipe The recipe, that sould be converted into a Document
     * @return File object, that points to the generated Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Parser, when anything with the Filesystem went wrong while parsing the PDF File
     */
    public File build(Recipe recipe) throws IOException, TexParserException {
        Cookbook myCookbook = new Cookbook();
        myCookbook.setTitle(recipe.getTitle());
        myCookbook.addRecipe(recipe);
        List<Sortlevel> sortlevels = new ArrayList<>();

        ((Cookbook) myCookbook).setSortlevel(sortlevels);

        File rootDir = config.getParserRootDir();
        File outputTexFile = config.getOutputTexFile(myCookbook.getTitle());
        File outputPdfFile = config.getOutputPdfFile(myCookbook.getTitle());
        File templateFile = config.getTemplateFile();
        File imgDir = config.getImageDir();

        createImage(recipe, imgDir);

        parseTexFile(outputTexFile, templateFile, imgDir, myCookbook);

        return createPDFFile(outputTexFile, outputPdfFile, rootDir);
    }


    /**
     * This Method creates a referencenumber and than  relates a recipe to its referencenumber via Hashmap. this is needed for the velocity template
     *
     * @param recipes   List of Recipes to generate a referencenumber for.
     * @param sortChain Sorted list of Sortlevels where the fist Listogject is the primary sortattribute of the Recipes. the refnum will be primarySortLevel.secondarySortLeve.thirdSortLevel...
     * @return Hashmap, that relates each Recipe to its referencenumber
     */
    private Map<Recipe, String> generateRefNumList(List<Recipe> recipes, List<Sortlevel> sortChain) {
        Map<Recipe, String> refNumList = new HashMap<>();
        Map<Recipe, Properties> propList = generateRecipePropertyList(recipes);
        String refNum = "";

        for (Recipe recipe : recipes) {
            refNum = "";
            for (Sortlevel sortLevel : sortChain) {
                String prop = sortLevel.getName().toLowerCase();
                refNum += propList.get(recipe).getProperty(prop) + ".";
            }

            refNumList.put(recipe, (refNum.equals("")) ? "" : refNum.substring(0, refNum.length() - 1));
        }
        return refNumList;
    }

    /**
     * This Method creates a referencenumber and than  relates a recipe to its referencenumber via Hashmap. this is needed for the velocity template
     *
     * @param recipes List of Recipes to generate a Properties Object for.
     * @return Hashmap, that relates each Recipe its sortable Properties, The Properties are category,season,nuture,curse and region
     */
    private Map<Recipe, Properties> generateRecipePropertyList(List<Recipe> recipes) { //TODO: Meybe get the List of Sortable Attributes out of database and work with reflections on recipe?
        Map<Recipe, Properties> propList = new HashMap<>();

        for (Recipe recipe : recipes) {
            Properties props = new Properties();
            props.setProperty("kategorie", (recipe.getCategory() == null) ? "" : recipe.getCategory().getName());
            props.setProperty("saison", (recipe.getSeason() == null) ? "" : recipe.getSeason().getName());
            props.setProperty("ernährungsart", (recipe.getNurture() == null) ? "" : recipe.getNurture().getName());
            props.setProperty("gerichtart", (recipe.getCourse() == null) ? "" : recipe.getCourse().getName());
            props.setProperty("region", (recipe.getRegion() == null) ? "" : recipe.getRegion().getName());
            props.setProperty("rezeptquelle", (recipe.getSource() == null) ? "" : recipe.getSource().getName());
            props.setProperty("tageszeit", (recipe.getDaytime() == null) ? "" : recipe.getDaytime().getName());
            propList.put(recipe, props);
        }
        return propList;
    }

    /**
     * This Method sais, which type of document this builder builds. In this case its "PDF"
     *
     * @param filetype filetype to check the building suitability
     * @return if this buider is suitable to build the given filetype
     */
    @Override
    public boolean builds(String filetype) {
        return filetype.equalsIgnoreCase("pdf");
    }
}

