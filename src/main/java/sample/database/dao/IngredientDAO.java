package sample.database.dao;

import sample.database.dbo.IngredientDBO;
import sample.model.Ingredient;

import java.util.Optional;

/**
 * Database Access Object for Ingredient.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class IngredientDAO extends ADAO<Ingredient, IngredientDBO> {

    @Override
    Ingredient toPOJO(IngredientDBO ingredientDBO) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setID(ingredientDBO.getID());
        ingredient.setName(ingredientDBO.getName());
        return ingredient;
    }

    @Override
    public IngredientDBO toDBO(Ingredient pojo) {
        IngredientDBO ingredientDBO = new IngredientDBO();
        Optional<Ingredient> ingredient = findFirst("name=?", pojo.getName());

        // if ingredient not present in DB then insert it.
        if(!ingredient.isPresent()){
            ingredientDBO.setName(pojo.getName());
            ingredientDBO.saveIt();
        }
        else {  // else read data from existing ingredient entry in DB
            ingredientDBO.setID(ingredient.get().getID());
            ingredientDBO.setName(ingredient.get().getName());
        }
        return ingredientDBO;
    }
}

