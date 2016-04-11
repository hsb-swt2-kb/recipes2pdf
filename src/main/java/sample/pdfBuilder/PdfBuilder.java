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

class PdfBuilder implements IPdfBuilder {


    private final PdfBuilderConfig config;

    PdfBuilder(PdfBuilderConfig config) {
        this.config = config;
    }

    private void parseTexFile(File outputTexFile, ICookbook cookbook) throws ConvertTemplatetoTexFailedException, IOException {
        JLRConverter converter = new JLRConverter(config.getTemplateDir());
        converter.replace("cookbook", cookbook);
        System.out.println(config.getTemplateDir());
        converter.replace("referenceNumber", "refNum"); //TODO: This is going to be hard to implement with the Template...
        converter.replace("imgDir", config.getImageDir().getAbsolutePath());
        if (!converter.parse(getTemplateFile(), outputTexFile)) {
            throw getConvertFailedException(cookbook, converter); //TODO: Display ErrorMessage in GUI?
        }
    }

    private ConvertTemplatetoTexFailedException getConvertFailedException(ICookbook cookbook, JLRConverter converter) {
        return new ConvertTemplatetoTexFailedException("Convert template to " + config.getOutputTexFile(cookbook.getTitle()) + " failed! Error Message:\n" + converter.getErrorMessage());
    }

    private File createPDFFile(File outputTexFile, ICookbook cookbook) throws IOException, GeneratePdfFailedException {

        JLRGenerator generator = new JLRGenerator();
        if (generator.generate(outputTexFile, config.getOutputDir(), config.getParserRootDir())) {
            return config.getOutputPdfFile(cookbook.getTitle());
        } else {
            throw getGeneratePdfFailedException(cookbook, generator);
        }
    }


    private GeneratePdfFailedException getGeneratePdfFailedException(ICookbook cookbook, JLRGenerator generator) {
        return new GeneratePdfFailedException("Parse \"" + config.getOutputTexFile(cookbook.getTitle()) + "\" to \"" + config.getOutputPdfFile(cookbook.getTitle()) + "\" failed! Error Message:\n" + generator.getErrorMessage());
    }



    private File getTemplateFile() {
        File template = new File(this.getClass().getClassLoader().getResource("sample/pdfBuilder/templates/cookbookTemplate.tex").getPath());
        File userTemplate = config.getUserTemplate();

        try {
            if (!config.getTemplateDir().exists()) {
                Files.createDirectory(config.getTemplateDir().toPath());
            }

            if (!userTemplate.exists()) {
                Files.copy(template.toPath(), userTemplate.toPath());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userTemplate;
    }

    private void createImage(Long recipeID) {
        try {
            byte[] img = Files.readAllBytes(config.getInputImage(recipeID).toPath());
            FileOutputStream outputStream = new FileOutputStream(config.getOutputImage(recipeID));
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public File buildPDF(ICookbook cookbook) throws IOException, GeneratePdfFailedException, ConvertTemplatetoTexFailedException {
        File outputTexFile = config.getOutputTexFile(cookbook.getTitle());
        for (IRecipe recipe : cookbook.getRecipes()) {
            createImage(recipe.getID());
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }

    public File buildPDF(IRecipe recipe) throws ConvertTemplatetoTexFailedException, IOException, GeneratePdfFailedException {
        ICookbook cookbook = ICookbook.getInstance();
        cookbook.addRecipe(recipe);

        File outputTexFile = config.getOutputTexFile(recipe.getTitle());
        for (IRecipe tempRecipe : cookbook.getRecipes()) {
            createImage(tempRecipe.getID());
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }
}
