package sample.ui;

import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CouldNotParseException;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.Recipe;
import sample.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * implemented by on 18.05.16 by  markus
 */
public class UI {
    static void start (String[] parameter){};

    static boolean addRecipes (List<File> files) throws FileNotFoundException,CouldNotParseException {
        boolean success=true;
        Recipe recipe = new Recipe();
        for(int i=0;i<files.size();i++) {
            recipe = Parser.getInstance().parse(files.get(i));
            if (recipe.isIncomplete())
                success = false;
            else
                if(!new RecipeDAO().insert(recipe))
                    success=false;
        }
        return success;
    }


    static boolean addRecipe (File file) throws FileNotFoundException,CouldNotParseException {
        boolean success=true;
        Recipe recipe = new Recipe();
        recipe = Parser.getInstance().parse(file);
        if (recipe.isIncomplete())
            success = false;
        else
        if(!new RecipeDAO().insert(recipe))
            success=false;
        return success;
    }

    static List<Recipe> getAllRecipesFromDB(){
        return new RecipeDAO().getAll();
    }

    static boolean delRecipes (ArrayList<Recipe> recipes){
        boolean success = true;
        for(int i=0;i<recipes.size();i++)
            if(!new RecipeDAO().delete(recipes.get(i)))
                success=false;
        return success;

    }

    /*
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     */
    static boolean createCookBook(String cookBookName,String pictureFileName,String preamble){
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(cookBookName);
        //cookbook.setPicture(pictureFileName);
        //cookbook.setPreamble(preamble);

        return new CookbookDAO().insert(cookbook);
    }

    static ArrayList<String> readFile(String file) throws IOException {
        String line;
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}
