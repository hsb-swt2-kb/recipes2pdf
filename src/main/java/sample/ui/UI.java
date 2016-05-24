package sample.ui;

import sample.database.dao.RecipeDAO;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.Recipe;
import sample.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by markus on 18.05.16.
 */
public interface UI {
    abstract void start (String[] parameter);

    default ArrayList<String> addRecipes (File[] files) throws FileNotFoundException {
        ArrayList<String> recipeNames = new ArrayList<String>();
        ArrayList<File> recipesNotFound = new ArrayList<File>();
        for(File f : files){
                recipeNames.add(addRecipes(f));
        }
        return recipeNames;
    }
    default String addRecipes (File recipeFile) throws FileNotFoundException {
        return Parser.getInstance().parse(recipeFile).getTitle();
    }

    default List<String> getAllRecipeNamesFromDB(){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        List<Recipe> recipeList = new RecipeDAO().getAll();
        List<String> recipeNames;
        //for (int i=0;i<recipeList.size();i++){
        //    recipeNames.add(recipeList.get(i).getTitle());
        //}
        //return recipeNames;
        return null;
    }

    default void delRecipes (String[] recipeNames){
        Recipe recipe = new Recipe();
        for(String recipeName : recipeNames){
            recipe.setTitle(recipeName);
            new RecipeDAO().delete(recipe);
        }
    }

    default void      showRecipe    (String   recipeName){


    }

    default void      editRecipe    (String   reciepeName){



    }

    default void      createCookBook(String   cookBookName,String pictureFileName,String preamble){
        new Cookbook().setTitle(cookBookName);
        //cookbook.setPicture(pictureFileName);
        //cookbook.setPreamble(preamble);
    }

    default ArrayList<String> readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        }
        finally {
            reader.close();
        }
    }
    default void      showCookBook  (String   cookBookName){

    }

    default ICookbook editCookBook  (String cookBookName,String keyAndValue){
        return null;
    }
}
