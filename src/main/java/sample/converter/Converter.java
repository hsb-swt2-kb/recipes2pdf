package sample.converter;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Kai Nortmann
 */

class Converter implements IConverter {


    private final ConverterConfig config;

    Converter(ConverterConfig config) {
        this.config = config;
    }

    Converter() {
        this.config = new ConverterConfig();
    }

    private void parseTexFile(File outputTexFile, ICookbook cookbook) throws IOException {
        JLRConverter converter = new JLRConverter(config.getTemplateDir());
        converter.replace("cookbook", cookbook);
        System.out.println(config.getTemplateDir());
        converter.replace("referenceNumber", "refNum"); //TODO: Generate Referencenumber out of cookbook refnum-rule (but how?)
        converter.replace("imgDir", config.getImageDir().getAbsolutePath());
        if (!converter.parse(config.getTemplateFile(), outputTexFile)) {
            new Exception("Convert template to " + config.getOutputTexFile(cookbook.getTitle()) + " failed! Error Message:\n" + converter.getErrorMessage()); //TODO: Display ErrorMessage in GUI?
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

    private void createImage(Long recipeID) {
        try {
            byte[] img = Files.readAllBytes(config.getInputImage().toPath());
            FileOutputStream outputStream = new FileOutputStream(config.getOutputImage(recipeID));
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File buildPDF(ICookbook cookbook) throws Exception {
        File outputTexFile = config.getOutputTexFile(cookbook.getTitle());

        for (IRecipe recipe : cookbook.getRecipes()) {
            createImage(recipe.getID());
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }

    public File buildPDF(IRecipe recipe) throws Exception {
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
