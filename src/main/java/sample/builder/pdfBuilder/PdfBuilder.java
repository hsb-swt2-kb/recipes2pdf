package sample.builder.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;
import sample.builder.IConcreteBuilder;
import sample.config.IConfig;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.IRecipe;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

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
        converter.replace("cookbook", cookbook);
        System.out.println(config.getTemplateDir());
        converter.replace("referenceNumber", "refNum"); //TODO: Generate Referencenumber out of cookbook refnum-rule (but how?)
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

            FileOutputStream outputStream = new FileOutputStream(config.getImage());
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public File build(ICookbook cookbook) throws Exception {
        File outputTexFile = config.getOutputTexFile(cookbook.getTitle());

        for (IRecipe recipe : cookbook.getRecipes()) {
            createImage(recipe);
        }
        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }

    public File build(IRecipe recipe) throws Exception {
        ICookbook cookbook = new Cookbook();
        cookbook.addRecipe(recipe);

        File outputTexFile = config.getOutputTexFile(recipe.getTitle());

        createImage(recipe);

        parseTexFile(outputTexFile, cookbook);

        return createPDFFile(outputTexFile, cookbook);
    }

    @Override
    public boolean builds(String filetype) {
        return filetype.equalsIgnoreCase("pdf");
    }
}
