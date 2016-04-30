package sample.model;

import sample.model.activejdbc.CategoryDBO;
import sample.model.activejdbc.RecipeDBO;

import java.util.Optional;

/**
 * Created by czoeller on 28.04.16.
 */
public class RecipeDAO extends ADAO<Recipe, RecipeDBO> {
    /**
     * ID of recipe has to be null for insert.
     *
     * @param recipe
     */
    @Override
    public boolean insert(Recipe recipe) {
        if( null != recipe.getID() ) {
            throw new IllegalStateException("A new Recipe has no id!");
        }
        final RecipeDBO recipeDBO = toDBO(recipe);
        return recipeDBO.saveIt();
    }

    @Override
    public boolean update(Recipe recipe) {
        return toDBO(recipe).saveIt();
    }

    @Override
    public boolean delete(Recipe recipe) {
        final Optional<RecipeDBO> recipeOptional = Optional.ofNullable(RecipeDBO.findById(recipe.getID()));
        return recipeOptional.orElseThrow( () -> new IllegalStateException ("Recipe with id not found to delete.")).delete();
    }

    @Override
    Recipe toPOJO(RecipeDBO recipeDBO) {
        final Recipe recipe = new Recipe( recipeDBO.getID() );
        recipe.setTitle( recipeDBO.getTitle() );
        return recipe;
    }

    @Override
    RecipeDBO toDBO(Recipe recipe) {
        final RecipeDBO recipeDBO = new RecipeDBO(recipe.getID());
        recipeDBO.setTitle(recipe.getTitle());

        final Category category = (Category) recipe.getCategory();
        if(category != null) {
            final CategoryDBO categoryDBO = new CategoryDAO().toDBO(category);
            recipeDBO.setCategory( categoryDBO );
        }
        return recipeDBO;
    }
}
