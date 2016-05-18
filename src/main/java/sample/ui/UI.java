package sample.ui;
import sample.model.ICookbook;
/**
 * Created by markus on 18.05.16.
 */
public interface UI
{
    void      start(String[] parameter);
    void      addRecipes(String dirPath);
    void      addRecipes(String[] filenamesWithPath);
    void      delRecipes(String[] recipeNames);
    void      showRecipe(String recipeName);
    void      editRecipe(String recipeName);
    void      createCookBook(String cookBookName,String pictureFileName,String preamble);
    String    showCookBook(String cookBookName);
    ICookbook editCookBook(String cookBookName,String keyAndValue);
    void      delCookBook(String cookBookName);
    void      export(String cookBookName,String fileType,String format);
    //void    export(String recipeName,String fileType,String format);
}
