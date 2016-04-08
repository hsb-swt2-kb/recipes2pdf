package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;
import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kai Nortmann
 */

class PdfBuilder implements IPdfBuilder { //TODO: Exceptionhandling

    private List<File> createPDFFiles(ICookbook cookbook) {
        ArrayList<File> outputPDFFiles = new ArrayList<>();
        for (IRecipe recipe : cookbook.getRecipes()) {
            outputPDFFiles.add(buildPDF(recipe));
        }
        return outputPDFFiles;
    }

    private void parseTexFile(File outputTexFile, IRecipe recipe) {
        JLRConverter converter = new JLRConverter(getTemplateDir());
        converter.replace("recipe", recipe);
        converter.replace("referenceNumber", "refNum"); //TODO: Generate Referencenumber out of Cookbook
        converter.replace("imgPath", getOutputImage(recipe.getID()).getAbsolutePath());
        try {
            if (!converter.parse(getTemplateFile(), outputTexFile)) {
                System.out.println(converter.getErrorMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createPDFFile(File outputTexFile, IRecipe recipe_obj) {
        JLRGenerator generator = new JLRGenerator();
        try {
            if (generator.generate(outputTexFile, getOutputDir(), getParserRootDir())) {

                return getOutputPdfFile(recipe_obj.getID());
            } else {
                System.out.println("Generieren Fehlgeschlagen! " + generator.getErrorMessage());
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; //TODO: is that right here?
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

    private File getOutputTexFile(Long id) {
        return new File(getOutputDir().getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "_" + id + Config.OUTPUT_TEX_FILETYPE);
    }

    private File getOutputPdfFile(Long id) {
        return new File(getOutputDir().getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "_" + id + Config.OUTPUT_PDF_FILETYPE);
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

    public File buildPDF(ICookbook cookbook) {
        PDFMergerUtility merger = new PDFMergerUtility();
        for (File pdfDoc : createPDFFiles(cookbook)) {
            merger.addSource(pdfDoc);
        }
        merger.setDestinationFileName(getOutputDir().getAbsolutePath() + File.separator + "kochbuch.pdf");

        try {
            merger.mergeDocuments();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (COSVisitorException e) {
            e.printStackTrace();
        }
        return new File(getOutputDir().getAbsolutePath() + File.separator + "kochbuch.pdf");
    }

    public File buildPDF(IRecipe recipe) {
        File outputTexFile = getOutputTexFile(recipe.getID());
        createImage(recipe.getID());

        parseTexFile(outputTexFile, recipe);
        return createPDFFile(outputTexFile, recipe);
    }
}
