package sample.database.dao;

import sample.database.dbo.CookbookDBO;
import sample.database.dbo.RecipeDBO;
import sample.model.Cookbook;
import sample.model.IRecipe;
import sample.model.Recipe;

import java.util.List;

/**
 * Created by czoeller on 02.05.2016.
 */
public class CookbookDAO extends ADAO<Cookbook, CookbookDBO> {

    @Override
    Cookbook toPOJO(CookbookDBO cookbookDBO) {
        final Cookbook cookbook = new Cookbook();
        cookbook.setID(cookbookDBO.getID());
        cookbook.setTitle(cookbookDBO.getTitle());

        List<IRecipe> recipes = cookbookDBO.getRecipes();
        if(null != recipes) {
            for(IRecipe recipe : recipes) {
                cookbook.addRecipe( new RecipeDAO().toPOJO((RecipeDBO) recipe) );
            }
        }

        return cookbook;
    }

    @Override
    CookbookDBO toDBO(Cookbook pojo) {
        CookbookDBO cookbookDBO = new CookbookDBO();
        if (findById(pojo.getID()).isPresent()) {
            cookbookDBO.setID(pojo.getID());
        }
        cookbookDBO.setTitle(pojo.getTitle());
        cookbookDBO.saveIt();

        List<IRecipe> recipes = pojo.getRecipes();
        if(null != recipes) {
            for(IRecipe recipe : recipes) {
                cookbookDBO.addRecipe( new RecipeDAO().toDBO( (Recipe) recipe) );
            }
        }

        return cookbookDBO;
    }
}
