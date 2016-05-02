package sample.model;

import sample.model.activejdbc.CategoryDBO;
import sample.model.activejdbc.RecipeDBO;

/**
 * Created by czoeller on 28.04.16.
 */
public class RecipeDAO extends ADAO<Recipe, RecipeDBO> {

    @Override
    Recipe toPOJO(RecipeDBO recipeDBO) {
        final Recipe recipe = new Recipe();
        recipe.setID(recipeDBO.getID());
        recipe.setTitle( recipeDBO.getTitle() );

        final CategoryDBO categoryDBO = (CategoryDBO) recipeDBO.getCategory();
        if(categoryDBO != null) {
            final Category category = new CategoryDAO().toPOJO(categoryDBO);
            recipe.setCategory( category );
        }

        return recipe;
    }

    @Override
    RecipeDBO toDBO(Recipe recipe) {
        final RecipeDBO recipeDBO = new RecipeDBO();
        recipeDBO.setID(recipe.getID());
        recipeDBO.setTitle(recipe.getTitle());

        final Category category = (Category) recipe.getCategory();
        if(category != null) {
            final CategoryDBO categoryDBO = new CategoryDAO().toDBO(category);
            recipeDBO.setCategory( categoryDBO );
        }
        return recipeDBO;
    }
}
