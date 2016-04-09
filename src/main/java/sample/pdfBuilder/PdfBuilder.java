package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.pdfBuilder.exceptions.ConvertTemplatetoTexFailedException;
import sample.pdfBuilder.exceptions.GeneratePdfFailedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Kai Nortmann
 */

class PdfBuilder implements IPdfBuilder { //TODO: Exceptionhandling

//    private List<File> createPDFFiles(ICookbook cookbook) {
//        ArrayList<File> outputPDFFiles = new ArrayList<>();
//        for (IRecipe recipe : cookbook.getRecipes()) {
//            outputPDFFiles.add(buildPDF(recipe));
//        }
//        return outputPDFFiles;
//    }

    private void parseTexFile(File outputTexFile, ICookbook cookbook) throws ConvertTemplatetoTexFailedException, IOException {
        JLRConverter converter = new JLRConverter(getTemplateDir());
        converter.replace("cookbook", cookbook);

        converter.replace("referenceNumber", "refNum"); //TODO: This is going to be hard to implement with the Template...
        converter.replace("imgDir", getImageDir().getAbsolutePath());
        if (!converter.parse(getTemplateFile(), outputTexFile)) {
            throw getConvertFailedException(cookbook, converter); //TODO: Display ErrorMessage in GUI?
        }
    }

    private ConvertTemplatetoTexFailedException getConvertFailedException(ICookbook cookbook, JLRConverter converter) {
        return new ConvertTemplatetoTexFailedException("Convert template to " + getOutputTexFile(cookbook.getTitle()) + " failed! Error Message:\n" + converter.getErrorMessage());
    }

    private File createPDFFile(File outputTexFile, ICookbook cookbook) throws IOException, GeneratePdfFailedException {

        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(outputTexFile, getOutputDir(), getParserRootDir())) {
            return getOutputPdfFile(cookbook.getTitle());
        } else {
            throw getGeneratePdfFailedException(cookbook, generator);
        }
    }


    private GeneratePdfFailedException getGeneratePdfFailedException(ICookbook cookbook, JLRGenerator generator) {
        return new GeneratePdfFailedException("Parse \"" + getOutputTexFile(cookbook.getTitle()) + "\" to \"" + getOutputPdfFile(cookbook.getTitle()) + "\" failed! Error Message:\n" + generator.getErrorMessage());
    }


    private File getTemplateFile() {
        File template = new File(this.getClass().getClassLoader().getResource(Config.RESSOURCE_PATH + File.separator + Config.TEMPLATE_FOLDER_NAME + File.separator + Config.TEMPLATE_FILE_NAME).getPath());
        File userTemplate = new File(getParserRootDir().getAbsolutePath() + File.separator + Config.TEMPLATE_FOLDER_NAME + File.separator + Config.TEMPLATE_FILE_NAME);

        try {
            if (!getTemplateDir().exists()) {
                Files.createDirectory(getTemplateDir().toPath());
            }

            if (!userTemplate.exists()) {
                Files.copy(template.toPath(), userTemplate.toPath());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userTemplate;
    }

    private File getParserRootDir() {
        return new File(Config.RESSOURCE_USER_PATH);
    }

    private File getImageDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.IMAGE_FOLDER_NAME);
    }

    private File getOutputImage(Long ImageID) {
        ImageID = new Long(1); //TODO: Just for testing, delete later, when recipes come out of database
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + "_out" + Config.IMAGE_FILETYPE);
    }

    private File getInputImage(Long ImageID) {
        ImageID = new Long(1); //TODO: Just for testing, delete later, when recipes come out of database
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.IMAGE_FOLDER_NAME + File.separator + Config.IMAGE_PREFIX + ImageID + Config.IMAGE_FILETYPE);
    }

    private File getTempDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.TEMP_FOLDER_NAME);
    }

    private File getTemplateDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.TEMPLATE_FOLDER_NAME);
    }

    private File getOutputDir() {
        return new File(Config.RESSOURCE_USER_PATH + File.separator + Config.OUTPUT_FOLDER_NAME);
    }

    private File getOutputTexFile(String cookbookName) {
        return new File(getOutputDir().getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "_" + cookbookName + Config.OUTPUT_TEX_FILETYPE);
    }

    private File getOutputPdfFile(String cookbookName) {
        return new File(getOutputDir().getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "_" + cookbookName + Config.OUTPUT_PDF_FILETYPE);
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


    public File buildPDF(ICookbook cookbook) throws IOException, GeneratePdfFailedException, ConvertTemplatetoTexFailedException {
        File outputTexFile = getOutputTexFile(cookbook.getTitle());
        for (IRecipe recipe : cookbook.getRecipes()) {
            createImage(recipe.getID());
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }

    public File buildPDF(IRecipe recipe) throws ConvertTemplatetoTexFailedException, IOException, GeneratePdfFailedException {
        ICookbook cookbook = ICookbook.getInstance();
        cookbook.addRecipe(recipe);

        File outputTexFile = getOutputTexFile(recipe.getTitle());
        for (IRecipe tempRecipe : cookbook.getRecipes()) {
            createImage(tempRecipe.getID());
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }
}
