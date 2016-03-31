package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Kai Nortmann
 */
class PdfBuilder {
    void buildPDF() {
        String fileseperator = File.separator;
        int currentNumber = 1;
        Recipe recipe_obj = new Recipe();

        File template = new File(this.getClass().getClassLoader().getResource("sample/pdfBuilder/templates/recipeTemplate.tex").getPath());

        File parseRootDirectory = template.getParentFile().getParentFile();
        String str_parseRootDirectory = parseRootDirectory.getAbsolutePath() + File.separator;

        File imageDir = new File(str_parseRootDirectory + Config.IMAGE_FOLDER_NAME);

        //TODO: File generating out of database
        File imgFile = new File(imageDir.getAbsolutePath() + File.separator + Config.IMAGE_PREFIX + recipe_obj.getID() + Config.IMAGE_FILETYPE);

        File outputImg = new File(imageDir.getAbsolutePath() + File.separator + Config.IMAGE_PREFIX + recipe_obj.getID() + "_out" + Config.IMAGE_FILETYPE);
        System.out.println(outputImg);
        File tempDir = new File(str_parseRootDirectory + Config.TEMP_FOLDER_NAME);
        File templateDir = new File(str_parseRootDirectory + Config.TEMPLATE_FOLDER_NAME);
        File outputDir = new File(str_parseRootDirectory + Config.OUTPUT_FOLDER_NAME);

        //TODO: Excaptionhandling for missing directories or files (like template-file)

        try {
            byte[] img = Files.readAllBytes(imgFile.toPath());
            FileOutputStream outputStream = new FileOutputStream(outputImg);
            outputStream.write(img);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File recipe1 = new File(tempDir.getAbsolutePath() + File.separator + Config.OUTPUT_FILE_PREFIX + "1" + Config.OUTPUT_FILETYPE);
        System.out.println(recipe1);
        JLRConverter converter = new JLRConverter(templateDir);
        JLRGenerator generator = new JLRGenerator();

        converter.replace("ingredientList", recipe_obj.getIngredients());
        converter.replace("centerHead", recipe_obj.getCategory());
        converter.replace("referenceNumber", recipe_obj.getCategoryNumber() + "." + currentNumber);
        converter.replace("imgPath", outputImg.getAbsolutePath());

        try {
            converter.parse(template, recipe1);
            generator.generate(recipe1, outputDir, parseRootDirectory);
            System.out.println(converter.getErrorMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
