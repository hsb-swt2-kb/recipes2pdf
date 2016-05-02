package sample.model;

import sample.model.activejdbc.IngredientDBO;

/**
 * Created by czoeller on 02.05.2016.
 */
public class IngredientDAO extends ADAO<Ingredient, IngredientDBO> {

    @Override
    Ingredient toPOJO(IngredientDBO ingredientDBO) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setID(ingredientDBO.getID());
        ingredient.setName( ingredientDBO.getName() );
        return ingredient;
    }

    @Override
    public IngredientDBO toDBO(Ingredient pojo) {
        IngredientDBO ingredientDBO = new IngredientDBO();
        if( findById( pojo.getID( ) ).isPresent()  ) {
            ingredientDBO.setID( pojo.getID() ) ;
        }

        ingredientDBO.setName(pojo.getName());
        return ingredientDBO;
    }
}

