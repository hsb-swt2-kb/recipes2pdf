package sample.pdfBuilder;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Kai Nortmann
 */
class PdfBuilder {
    void buildPDF() {
        String fileseperator = File.separator;


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

//        ArrayList<ArrayList<String>> ingredientList = new ArrayList<>();
//
//        ArrayList<String> ingredient1 = new ArrayList<>();
//        ArrayList<String> ingredient2 = new ArrayList<>();
//        ArrayList<String> ingredient3 = new ArrayList<>();
//
//        ingredient1.add("Mehl");
//        ingredient1.add("400");
//        ingredient2.add("Wasser");
//        ingredient2.add("200");
//        ingredient3.add("Hefe");
//        ingredient3.add("1");
//
//        ingredientList.add(ingredient1);
//        ingredientList.add(ingredient2);
//        ingredientList.add(ingredient3);

        converter.replace("ingredientList", new Recipe().getIngredients());
        try {
            converter.parse(template, recipe1);
            generator.generate(recipe1, outputDir, parseRootDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Recipe {
        public ArrayList<Ingredient> getIngredients() {
            ArrayList<Ingredient> ingredientlist = new ArrayList<>();
            ingredientlist.add(new Ingredient());
            return ingredientlist;
        }
    }

    class Ingredient {
        public String getName() {
            return "Mehl";
        }

        public int getAmount() {
            return 500;
        }

        public String getUnig() {
            return "g";
        }
    }
}
