package sample.ui;

import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
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
public interface UI {
    abstract void start (String[] parameter);

    default boolean addRecipes (ArrayList<File> files) throws FileNotFoundException {
        boolean success=true;
        for(File f : files)
            if(!Parser.getInstance().parse(f))
                success=false;
        return success;
    }


    default boolean addRecipe (File file) throws FileNotFoundException {
            if(!Parser.getInstance().parse(file))
                return false;
        return true;
    }

    default boolean addRecipes (File recipeFile) throws FileNotFoundException {
        return Parser.getInstance().parse(recipeFile);
    }

    default List<Recipe> getAllRecipesFromDB(){
        return new RecipeDAO().getAll();
    }

    default boolean delRecipes (ArrayList<Recipe> recipes){
        boolean success = true;
        for(int i=0;i<recipes.size();i++)
            if(!new RecipeDAO().delete(recipes.get(i)))
                success=false;
        return success;

    }

    abstract void showRecipe (String recipeName);

    default boolean editRecipe (Recipe recipe){
        return new RecipeDAO().update(recipe);
    }

    /*
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     */
    default boolean createCookBook(String cookBookName,String pictureFileName,String preamble){
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(cookBookName);
        //cookbook.setPicture(pictureFileName);
        //cookbook.setPreamble(preamble);

        return new CookbookDAO().insert(cookbook);
    }

    default ArrayList<String> readFile(String file) throws IOException {
        String line;
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
    abstract void      showCookBook  (String   cookBookName);

    abstract ICookbook editCookBook  (String cookBookName,String keyAndValue);
}
