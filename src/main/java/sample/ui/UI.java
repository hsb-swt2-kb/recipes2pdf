package sample.ui;

import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.Recipe;
import sample.parser.Parser;

import java.io.*;
import java.util.ArrayList;

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

    void addRecipes (String[] filenamesWithPath){
        try{
            Parser parser = new Parser();
            for( String file:filenamesWithPath ){
                // parse file -> Recipe
                Recipe recipe = parser.parse(new File(file));
                // save recipe in DB.
                new RecipeDAO().insert(recipe);
            }
        }
        catch(FileNotFoundException e) {
            // TODO: FileNotFoundException behandeln
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
