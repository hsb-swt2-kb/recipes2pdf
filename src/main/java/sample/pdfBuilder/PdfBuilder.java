package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;

import java.io.File;
import java.io.IOException;

/**
 * @author Kai Nortmann
 */
class PdfBuilder {
    void buildPDF() {
        String fileseperator = File.separator;
        int currentNumber = 1;

        File template = new File(this.getClass().getClassLoader().getResource("sample/pdfBuilder/templates/recipeTemplate.tex").getPath());

        File parseRootDirectory = template.getParentFile().getParentFile();
        String str_parseRootDirectory = parseRootDirectory.getAbsolutePath() + File.separator;

        File tempDir = new File(str_parseRootDirectory + File.separator + Config.TEMP_FOLDER_NAME);
        File templateDir = new File(str_parseRootDirectory + File.separator + Config.TEMPLATE_FOLDER_NAME);
        File outputDir = new File(str_parseRootDirectory + File.separator + Config.OUTPUT_FOLDER_NAME);

        //TODO: Excaptionhandling for missing directories or files (like template-file)

        File recipe1 = new File(tempDir.getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "1" + Config.OUTPUT_FILETYPE);
        System.out.println(recipe1);
        JLRConverter converter = new JLRConverter(templateDir);
        JLRGenerator generator = new JLRGenerator();

        converter.replace("ingredientList", new Recipe().getIngredients());
        converter.replace("centerHead", new Recipe().getCategory());
        converter.replace("referenceNumber", new Recipe().getCategoryNumber() + "." + currentNumber);

        try {
            converter.parse(template, recipe1);
            generator.generate(recipe1, outputDir, parseRootDirectory);
            System.out.println(converter.getErrorMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
