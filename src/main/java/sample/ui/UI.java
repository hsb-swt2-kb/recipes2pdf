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
 * Created by markus on 18.05.16.
 */
abstract class UI {
    abstract void start (String[] parameter);

    void      addRecipes    (String   dirPath){
        File file=new File(dirPath);
        for(File f : file.listFiles()){
            addRecipes(f.getAbsolutePath());
        }
    }


    void delRecipes (String[] recipeNames){
        Recipe recipe = new Recipe();
        for(String recipeName : recipeNames){
            recipe.setTitle(recipeName);
            new RecipeDAO().delete(recipe);
        }
    }

    void      showRecipe    (String   recipeName){


    }

    void      editRecipe    (String   reciepeName){



    }

    void      createCookBook(String   cookBookName,String pictureFileName,String preamble){
        new Cookbook().setTitle(cookBookName);
        //cookbook.setPicture(pictureFileName);
        //cookbook.setPreamble(preamble);
    }

    protected ArrayList<String> readFile(String file) throws IOException {
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
    void      showCookBook  (String   cookBookName){

    }

    ICookbook editCookBook  (String cookBookName,String keyAndValue){
        return null;
    }
}
