package sample.model.dao;

import org.apache.commons.lang3.tuple.Triple;
import sample.model.Category;
import sample.model.IIngredient;
import sample.model.IUnit;
import sample.model.Recipe;
import sample.model.activejdbc.CategoryDBO;
import sample.model.activejdbc.RecipeDBO;

import java.util.List;

/**
 * Created by czoeller on 28.04.16.
 */
public class RecipeDAO extends ADAO<Recipe, RecipeDBO> {

    @Override
    Recipe toPOJO(RecipeDBO recipeDBO) {
        final Recipe recipe = new Recipe();
        recipe.setID(recipeDBO.getID());
        recipe.setTitle(recipeDBO.getTitle());

        final CategoryDBO categoryDBO = (CategoryDBO) recipeDBO.getCategory();
        if (categoryDBO != null) {
            final Category category = new CategoryDAO().toPOJO(categoryDBO);
            recipe.setCategory(category);
        }

        return recipe;
    }

    @Override
    RecipeDBO toDBO(Recipe recipe) {
        final RecipeDBO recipeDBO = new RecipeDBO();
        recipeDBO.setID(recipe.getID());
        recipeDBO.setTitle(recipe.getTitle());
        recipeDBO.saveIt(); // IMPORTANT to attach ingredients and so on

        final Category category = (Category) recipe.getCategory();
        if (category != null) {
            final CategoryDBO categoryDBO = new CategoryDAO().toDBO(category);
            recipeDBO.setCategory(categoryDBO);
        }

        final List<Triple<IIngredient, Integer, IUnit>> ingredients = recipe.getIngredients();
        for ( Triple<IIngredient, Integer, IUnit> triple : ingredients) {
            recipeDBO.add(triple.getLeft().getName(), triple.getMiddle(), triple.getRight().getName());
        }
        return recipeDBO;
    }
}
